package com.community.member.member.service;

import com.community.member.global.util.RedisUtil;
import com.community.member.member.domain.dto.MemberDTO;
import com.community.member.member.domain.dto.MemberInterestsDTO;
import com.community.member.member.domain.entity.*;
import com.community.member.member.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final MemberInterestsRepository memberInterestsRepository;
    private final PasswordQuestionRepository passwordQuestionRepository;
    private final IndustryRepository industryRepository;
    private final PasswordEncoder encoder;
    private final RedisUtil redisUtil;

    @Transactional
    public MemberDTO.MemberResponse signup(MemberDTO.AddMemberRequest request) {
        //UUID 자동 생성
        String createUUID = UUID.randomUUID().toString();
        //기본 USER 권한 추가
        MemberRole USER = memberRoleRepository.findByRoleName("USER").orElseThrow();
        PasswordQuestion question = passwordQuestionRepository.findById(request.getPasswordQuestionId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 질문 입니다."));

        //닉네임 유효성 검사
        if (memberRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 등록된 닉네임 입니다.");
        }
        //이메일 유효성 검사
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 등록된 이메일 입니다.");
        }
        //비밀번호 유효성 검사
        if (validatePassword(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
        //앞자리 한 글자로 변환
        if(request.getGender().length() > 1) {
            request.setGender(request.getGender().substring(0,1));
        }

        //회원 정보 저장
        Member member = memberRepository.save(Member.builder()
                .memberId(createUUID)
                .name(request.getName())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .gender(request.getGender())
                .phone(request.getPhone())
                .passwordQuestion(question)
                .findPasswordAnswer(request.getFindPasswordAnswer())
                .memberRole(USER)
                .build());

        //레디스 6980 포트로 전송
        redisUtil.setDocumentData(request.getEmail(),
                MemberDTO.RedisObject.builder().nickname(request.getNickname()).memberRoleName(USER.getRoleName()).build());

        return addIndustryList(request.getIndustries(), member);
    }

    @Transactional
    public MemberDTO.MemberResponse update(String email, MemberDTO.ModifyInfoRequest request) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("잘못된 사용자 입니다."));
        PasswordQuestion question = passwordQuestionRepository.findById(request.getPasswordQuestionId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 질문 입니다."));

        if (!member.getEmail().equals(email)) throw new RuntimeException("수정은 본인만 가능 합니다.");

        if (request.getNickname() == null || request.getFindPasswordAnswer() == null || request.getPasswordQuestionId() == null || request.getIndustriesId() == null) {
            throw new IllegalArgumentException("빈공간이 존재합니다.");
        }
        //빈공간 및 같은 값은 수정 하지 않음.
        if(!(request.getNickname().isEmpty() || request.getNickname().isBlank() || request.getNickname().equals(member.getNickname()))) {
            if (memberRepository.existsByNickname(request.getNickname())) throw new IllegalArgumentException("이미 등록된 닉네임 입니다.");
            member.setNickname(request.getNickname());
        }
        if(!(question.getQuestion().isBlank() || question.getQuestion().equals(member.getPasswordQuestion().getQuestion()))) {
            member.setPasswordQuestion(question);
        }
        if(!(request.getFindPasswordAnswer().isBlank() || request.getFindPasswordAnswer().isEmpty() || request.getFindPasswordAnswer().equals(member.getFindPasswordAnswer()))) {
            member.setFindPasswordAnswer(request.getFindPasswordAnswer());
        }

        //관심 업종 저장
        List<MemberInterests> list = memberInterestsRepository.findAllByMember(member);
        memberInterestsRepository.deleteAll(list);

        //레디스 6980 포트로 전송
        redisUtil.setDocumentData(email,
                MemberDTO.RedisObject.builder().nickname(request.getNickname()).memberRoleName("USER").build());

        return addIndustryList(request.getIndustriesId(), member);
    }

    @Transactional
    public boolean withdrawal(String email, MemberDTO.WithdrawalRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("잘못된 입력 입니다."));

        if (member.getEmail().equals(email)) throw new RuntimeException("삭제는 본인만 가능 합니다.");

        if(encoder.matches(request.getPassword(), member.getPassword())) {
            member.setWithdrawal(true);
            memberRepository.updateWithdrawal(member.getWithdrawal(), member.getEmail());
            return true;
        }
        return false;
    }

    public MemberDTO.UserInfoResponse userInfo(String email) {
        if (email == null || email.isEmpty()) throw new RuntimeException("비회원 입니다.");
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
        List<MemberInterestsDTO.InterestResponse> list = memberInterestsRepository.findAllByMember(member).stream().map(MemberInterestsDTO.InterestResponse::new).toList();
        return new MemberDTO.UserInfoResponse(member, list);
    }

    public MemberDTO.FindPasswordResponse findPassword(MemberDTO.FindPasswordRequest request) {
        if (request.getEmail() == null || request.getPasswordQuestionId() == null || request.getFindPasswordAnswer() == null) {
            throw new IllegalArgumentException("데이터가 비어 있습니다.");
        }
        if (request.getEmail().isEmpty() || request.getFindPasswordAnswer().isEmpty()) {
            throw new IllegalArgumentException("데이터가 비어 있습니다.");
        }
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
        if (!member.getPasswordQuestion().getPasswordQuestionId().equals(request.getPasswordQuestionId())) {
            throw new IllegalArgumentException("가입정보와 입력하신 데이터가 상이합니다.");
        }
        if (!member.getFindPasswordAnswer().equals(request.getFindPasswordAnswer())) {
            throw new IllegalArgumentException("가입정보와 입력하신 데이터가 상이합니다.");
        }
        return new MemberDTO.FindPasswordResponse(request);
    }

    @Transactional
    public MemberDTO.MemberResponse changePassword(MemberDTO.ChangePasswordRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) throw new IllegalArgumentException("데이터가 비어 있습니다.");
        if (request.getEmail().isEmpty() || request.getPassword().isEmpty()) throw new IllegalArgumentException("데이터가 비어 있습니다.");
        if (validatePassword(request.getPassword())) throw new IllegalArgumentException("비밀번호 유형이 올바르지 않습니다.");
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
        member.setPassword(encoder.encode(request.getPassword()));
        return new MemberDTO.MemberResponse(member);
    }

    //비밀번호 유효성 검사 메서드
    private boolean validatePassword(String password) {
        String regex = "^(?=.\\d)(?=.[A-Z])(?=.[a-z])(?=.[^\\w\\d\\s:])([^\\s]){8,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //산업 리스트 조회 및 추가
    private MemberDTO.MemberResponse addIndustryList(List<String> industriesId, Member member) {
        List<MemberInterests> interestsList = new ArrayList<>();
        List<Industry> industryList = industryRepository.findAllById(industriesId);
        for(Industry industry : industryList) {
            MemberInterests interest = MemberInterests.builder()
                    .industry(industry)
                    .member(member)
                    .build();
            interestsList.add(interest);
        }
        memberInterestsRepository.saveAll(interestsList);
        return new MemberDTO.MemberResponse(member);
    }
}
