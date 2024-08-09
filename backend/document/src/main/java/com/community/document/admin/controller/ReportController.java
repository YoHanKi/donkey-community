package com.community.document.admin.controller;

import com.community.document.admin.domain.dto.ReportDTO;
import com.community.document.admin.service.ReportService;
import com.community.document.global.dto.SuccessResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    //게시글 신고
    @PostMapping("/report/doc/{document_id}")
    public ResponseEntity<SuccessResult> reportDocument(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PathVariable("document_id") String documentId,
            @RequestBody ReportDTO.ReportRequest request, HttpServletRequest servletRequest) {
        String userIp = servletRequest.getRemoteAddr();
        reportService.reportDocument(userEmail, documentId, userIp, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "신고가 성공적으로 등록 되었습니다."));
    }

    //댓글 신고
    @PostMapping("/report/com/{comment_id}")
    public ResponseEntity<SuccessResult> reportComment(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @PathVariable("comment_id") String commentId,
            @RequestBody ReportDTO.ReportRequest request, HttpServletRequest servletRequest) {
        String userIp = servletRequest.getRemoteAddr();
        reportService.reportComment(userEmail, commentId, userIp, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "신고가 성공적으로 등록 되었습니다."));
    }

    //모두 조회
    @GetMapping("/admin/report")
    public ResponseEntity<List<ReportDTO.ReportResponse>> showAllReport(
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PageableDefault(size = 10, sort = "reportDate",
            direction = Sort.Direction.DESC ) Pageable pageable) {
        if(!userRole.equals("ADMIN")) {
            throw new IllegalArgumentException("잘못된 접근입니다. 권한이 없습니다.");
        }
        List<ReportDTO.ReportResponse> response = reportService.showAllReport(pageable);
        return ResponseEntity.ok().body(response);
    }

    //신고 승인
    @PutMapping("/admin/report/accept/{report_id}")
    public ResponseEntity<SuccessResult> acceptReport(
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PathVariable("report_id")String reportId) {
        if(!userRole.equals("ADMIN")) {
            throw new IllegalArgumentException("잘못된 접근입니다. 권한이 없습니다.");
        }
        reportService.acceptReport(reportId);
        return ResponseEntity.ok().body(new SuccessResult("성공", "신고가 성공적으로 처리 되었습니다."));
    }
}