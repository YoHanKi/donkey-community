package com.community.service;

import com.community.domain.dto.ReportRequest;
import com.community.domain.dto.ReportResponse;
import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.Member;
import com.community.domain.entity.Report;
import com.community.repository.CommentRepository;
import com.community.repository.DocumentRepository;
import com.community.repository.MemberRepository;
import com.community.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ReportService {

    private ReportRepository reportRepository;
    private CommentRepository commentRepository;
    private DocumentRepository documentRepository;
    private MemberRepository memberRepository;

    public ReportService(ReportRepository reportRepository, CommentRepository commentRepository, DocumentRepository documentRepository, MemberRepository memberRepository) {
        this.reportRepository = reportRepository;
        this.commentRepository = commentRepository;
        this.documentRepository = documentRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public ReportResponse reportDocument(String email, String documentId,String userIp, ReportRequest request) {
        //docId가 존재하는지 확인
        if(!documentRepository.findById(documentId).isPresent()) {
            throw new EntityNotFoundException("존재하지 않는 게시글 입니다.");
        } else if (reportRepository.countAllByReportThing(documentId) > 3) {
            throw new RuntimeException("이미 다량의 신고가 접수된 게시글 입니다.");
        }
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("존재하지 않는 사용자 입니다."));

        Report report = Report.builder()
                .reportId(UUID.randomUUID().toString())
                .reporterIp(userIp)
                .reportContent(request.getReportContent())
                .reporter(member)
                .reportType(1L)
                .reportThing(documentId)
                .build();


        return new ReportResponse(reportRepository.save(report));
    }

    @Transactional
    public ReportResponse reportComment(String email, String commentId,String userIp, ReportRequest request) {
        //comId가 존재하는지 확인
        if(!commentRepository.findById(commentId).isPresent()) {
            throw new EntityNotFoundException("존재하지 않는 댓글 입니다.");
        } else if (reportRepository.countAllByReportThing(commentId) > 3) {
            throw new RuntimeException("이미 다량의 신고가 접수된 게시글 입니다.");
        }
        Member member = memberRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("존재하지 않는 사용자 입니다."));

        Report report = Report.builder()
                .reportId(UUID.randomUUID().toString())
                .reporterIp(userIp)
                .reportContent(request.getReportContent())
                .reporter(member)
                .reportType(2L)
                .reportThing(commentId)
                .build();

        return new ReportResponse(reportRepository.save(report));
    }

    //신고 모두 조회
    public List<ReportResponse> showAllReport(Pageable pageable) {
        try {
            Slice<Report> reports = reportRepository.findAll(pageable);
            return reports.getContent().stream().map(ReportResponse::new).toList();
        } catch (Exception e) {
            throw new EntityNotFoundException("현재 신고가 없습니다.");
        }
    }

    //신고 승인
    @Transactional
    public ReportResponse acceptReport(String reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new EntityNotFoundException("해당 신고가 존재하지 않습니다."));

        if (report.getReportType() == 1L) {
            Document document = documentRepository.findById(report.getReportThing()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 게시글 입니다."));
            document.setDocVisible(false);
            report.setReportJudge(true);
            return new ReportResponse(report);
        } else if (report.getReportType() == 2L) {
            Comment comment = commentRepository.findById(report.getReportThing()).orElseThrow(()->new EntityNotFoundException("존재하지 않는 댓글 입니다."));
            comment.setCommentVisible(false);
            report.setReportJudge(true);
            return new ReportResponse(report);
        }
        throw new IllegalArgumentException("잘못된 형식의 신고 입니다.");
    }
}
