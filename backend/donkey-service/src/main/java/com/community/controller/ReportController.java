package com.community.controller;

import com.community.domain.dto.ReportRequest;
import com.community.domain.dto.ReportResponse;
import com.community.domain.dto.SuccessResult;
import com.community.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    //게시글 신고
    @PostMapping("/report/doc/{document_id}")
    public ResponseEntity<SuccessResult> reportDocument(@PathVariable("document_id") String documentId, @RequestBody ReportRequest request, HttpServletRequest servletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("회원 정보가 올바르지 않습니다."));
        String userIp = servletRequest.getRemoteAddr();
        reportService.reportDocument(email, documentId, userIp, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "신고가 성공적으로 등록 되었습니다."));
    }

    //댓글 신고
    @PostMapping("/report/com/{comment_id}")
    public ResponseEntity<SuccessResult> reportComment(@PathVariable("comment_id") String commentId, @RequestBody ReportRequest request, HttpServletRequest servletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("회원 정보가 올바르지 않습니다."));
        String userIp = servletRequest.getRemoteAddr();
        reportService.reportComment(email, commentId, userIp, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "신고가 성공적으로 등록 되었습니다."));
    }

    //모두 조회
    @GetMapping("/admin/report")
    public ResponseEntity<List<ReportResponse>> showAllReport(@PageableDefault(size = 10) Pageable pageable) {
        List<ReportResponse> response = reportService.showAllReport(pageable);
        return ResponseEntity.ok().body(response);
    }

    //신고 승인
    @PutMapping("/admin/report/accept/{report_id}")
    public ResponseEntity<SuccessResult> acceptReport(@PathVariable("report_id")String reportId) {
        reportService.acceptReport(reportId);
        return ResponseEntity.ok().body(new SuccessResult("성공", "신고가 성공적으로 처리 되었습니다."));
    }
}
