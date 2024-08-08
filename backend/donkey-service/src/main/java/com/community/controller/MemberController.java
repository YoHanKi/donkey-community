package com.community.controller;

import com.community.domain.dto.*;
import com.community.domain.entity.Member;
import com.community.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/register") //회원가입
    public ResponseEntity<SuccessResult> signup(@RequestBody AddMemberRequest request) {
        memberService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "회원 정보가 성공적으로 저장 되었습니다."));
    }

    @PutMapping("/member/modifyInfo") //회원정보 수정 (프론트 연결 시 AuthenticationPrincipal 확인)
    public ResponseEntity<SuccessResult> modify(@RequestBody ModifyInfoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        memberService.update(email, request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "회원 정보가 성공적으로 수정 되었습니다."));
    }

    @PutMapping("/member/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody WithdrawalRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        boolean result = memberService.withdrawal(email, request);
        if (result) {
            return ResponseEntity.ok().body(new SuccessResult("성공", "정상적으로 탈퇴 되었습니다."));
        }
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResult("실패", "탈퇴 중 문제가 발생하였습니다."));
    }

    //유저 정보 조회
    @GetMapping("/member/userinfo")
    public ResponseEntity<UserInfoResponse> userInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return ResponseEntity.ok().body(memberService.userInfo(email));
    }

    //유저 정보 조회
    @GetMapping("/member/userinfo/{email}")
    public ResponseEntity<UserInfoResponse> userInfo(@PathVariable("email") String email) {
        if (email == null) throw new IllegalArgumentException("잘못 입력 되었습니다.");
        return ResponseEntity.ok().body(memberService.userInfo(email));
    }

    //비밀번호 찾기 질문
    @PostMapping("/member/findpassword")
    public ResponseEntity<FindPasswordResponse> findPassword(@RequestBody FindPasswordRequest request) {
        return ResponseEntity.ok().body(memberService.findPassword(request));
    }

    //비밀번호 변경
    @PostMapping("/member/changepassword")
    public ResponseEntity<SuccessResult> changePassword(@RequestBody ChangePasswordRequest request) {
        memberService.changePassword(request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "성공적으로 수정되었습니다."));
    }
}
