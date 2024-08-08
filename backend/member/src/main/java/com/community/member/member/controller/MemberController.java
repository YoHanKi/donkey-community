package com.community.member.member.controller;

import com.community.member.global.dto.ErrorResult;
import com.community.member.global.dto.SuccessResult;
import com.community.member.member.domain.dto.MemberDTO;
import com.community.member.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/register") //회원가입
    public ResponseEntity<SuccessResult> signup(@RequestBody MemberDTO.AddMemberRequest request) {
        memberService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "회원 정보가 성공적으로 저장 되었습니다."));
    }

    @PutMapping("/modifyInfo") //회원정보 수정 (프론트 연결 시 AuthenticationPrincipal 확인)
    public ResponseEntity<SuccessResult> modify(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestBody MemberDTO.ModifyInfoRequest request) {
        memberService.update(userEmail, request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "회원 정보가 성공적으로 수정 되었습니다."));
    }

    @PutMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestBody MemberDTO.WithdrawalRequest request) throws IOException {
        boolean result = memberService.withdrawal(userEmail, request);
        if (result) {
            return ResponseEntity.ok().body(new SuccessResult("성공", "정상적으로 탈퇴 되었습니다."));
        }
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResult("실패", "탈퇴 중 문제가 발생하였습니다."));
    }

    //유저 정보 조회
    @GetMapping("/userinfo")
    public ResponseEntity<MemberDTO.UserInfoResponse> userInfo(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole
    ) {
        return ResponseEntity.ok().body(memberService.userInfo(userEmail));
    }

    //유저 정보 조회
    @GetMapping("/userinfo/{email}")
    public ResponseEntity<MemberDTO.UserInfoResponse> userInfo(@PathVariable("email") String email) {
        if (email == null) throw new IllegalArgumentException("잘못 입력 되었습니다.");
        return ResponseEntity.ok().body(memberService.userInfo(email));
    }

    //비밀번호 찾기 질문
    @PostMapping("/findpassword")
    public ResponseEntity<MemberDTO.FindPasswordResponse> findPassword(@RequestBody MemberDTO.FindPasswordRequest request) {
        return ResponseEntity.ok().body(memberService.findPassword(request));
    }

    //비밀번호 변경
    @PostMapping("/changepassword")
    public ResponseEntity<SuccessResult> changePassword(@RequestBody MemberDTO.ChangePasswordRequest request) {
        memberService.changePassword(request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "성공적으로 수정되었습니다."));
    }
}