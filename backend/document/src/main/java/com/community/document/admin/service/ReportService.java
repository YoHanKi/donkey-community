package com.community.document.admin.service;

import com.community.document.admin.domain.dto.ReportDTO;
import com.community.document.admin.domain.entity.Report;
import com.community.document.admin.repository.ReportRepository;
import com.community.document.document.domain.entity.Comment;
import com.community.document.document.domain.entity.Document;
import com.community.document.document.repository.CommentRepository;
import com.community.document.document.repository.DocumentRepository;
import com.community.document.global.util.RedisUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final CommentRepository commentRepository;
    private final DocumentRepository documentRepository;


    @Transactional
    public ReportDTO.ReportResponse reportDocument(String email, String documentId, String userIp, ReportDTO.ReportRequest request) {
        //docId가 존재하는지 확인
        if(!documentRepository.findById(documentId).isPresent()) {
            throw new EntityNotFoundException("존재하지 않는 게시글 입니다.");
        } else if (reportRepository.countAllByReportThing(documentId) > 3) {
            throw new RuntimeException("이미 다량의 신고가 접수된 게시글 입니다.");
        }


        Report report = Report.builder()
                .reportId(UUID.randomUUID().toString())
                .reporterIp(userIp)
                .reportContent(request.getReportContent())
                .reporterEmail(email)
                .reportType(1L)
                .reportThing(documentId)
                .build();


        return new ReportDTO.ReportResponse(reportRepository.save(report));
    }

    @Transactional
    public ReportDTO.ReportResponse reportComment(String email, String commentId, String userIp, ReportDTO.ReportRequest request) {
        //comId가 존재하는지 확인
        if(!commentRepository.findById(commentId).isPresent()) {
            throw new EntityNotFoundException("존재하지 않는 댓글 입니다.");
        } else if (reportRepository.countAllByReportThing(commentId) > 3) {
            throw new RuntimeException("이미 다량의 신고가 접수된 게시글 입니다.");
        }

        Report report = Report.builder()
                .reportId(UUID.randomUUID().toString())
                .reporterIp(userIp)
                .reportContent(request.getReportContent())
                .reporterEmail(email)
                .reportType(2L)
                .reportThing(commentId)
                .build();

        return new ReportDTO.ReportResponse(reportRepository.save(report));
    }

    //신고 모두 조회
    public List<ReportDTO.ReportResponse> showAllReport(Pageable pageable) {
        try {
            Slice<Report> reports = reportRepository.findAll(pageable);
            return reports.getContent().stream().map(ReportDTO.ReportResponse::new).toList();
        } catch (Exception e) {
            throw new EntityNotFoundException("현재 신고가 없습니다.");
        }
    }

    //신고 승인
    @Transactional
    public ReportDTO.ReportResponse acceptReport(String reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new EntityNotFoundException("해당 신고가 존재하지 않습니다."));

        if (report.getReportType() == 1L) {
            Document document = documentRepository.findById(report.getReportThing()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 게시글 입니다."));
            document.setDocVisible(false);
            report.setReportJudge(true);
            return new ReportDTO.ReportResponse(report);
        } else if (report.getReportType() == 2L) {
            Comment comment = commentRepository.findById(report.getReportThing()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 댓글 입니다."));
            comment.setCommentVisible(false);
            report.setReportJudge(true);
            return new ReportDTO.ReportResponse(report);
        }
        throw new IllegalArgumentException("잘못된 형식의 신고 입니다.");
    }
}