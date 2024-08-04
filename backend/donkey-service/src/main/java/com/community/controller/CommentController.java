package com.community.controller;

import com.community.domain.dto.*;
import com.community.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 리스트 가져오기
    @GetMapping("/comment/list/{doc_id}")
    public ResponseEntity<List<FindCommentResponse>> showCommentAll(@PathVariable("doc_id") String docId) {
        List<FindCommentResponse> list = commentService.findAllByDocs(docId);
        return ResponseEntity.ok().body(list);
    }

    // 댓글 단건 조회
    @GetMapping("/comment/{comment_id}")
    public ResponseEntity<FindCommentResponse> showOneComment(@PathVariable("comment_id") String commentId) {
        return ResponseEntity.ok().body(commentService.findOneById(commentId));
    }

    //댓글 생성
    @PostMapping("/comment/{doc_id}")
    public ResponseEntity<SuccessResult> saveComment(@PathVariable("doc_id") String docId, @RequestBody AddCommentRequest request, HttpServletRequest servletRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //아이피 가져오기
        request.setCommentCreatorIp(servletRequest.getRemoteAddr());
        //접속 중인 사용자의 데이터 가져오기
        String email = "";
        if (!authentication.getPrincipal().equals("ANONYMOUS_USER")) email = authentication.getName();
        commentService.saveComment(email ,docId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "댓글이 정상적으로 등록 되었습니다."));
    }

    //댓글 수정
    @PutMapping("/comment/{comment_id}")
    public ResponseEntity<SuccessResult> modifyComment(@PathVariable("comment_id") String commentId, @RequestBody ModifyCommentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (!authentication.getPrincipal().equals("ANONYMOUS_USER")) {email = authentication.getName();}
        commentService.updateComment(email, commentId, request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "댓글이 정상적으로 수정 되었습니다."));
    }

    //댓글 삭제
    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<SuccessResult> deleteComment(@PathVariable("comment_id") String commentId, @RequestBody DeleteCommentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = "";
        if (!authentication.getPrincipal().equals("ANONYMOUS_USER")) {email = authentication.getName();}
        commentService.deleteComment(email, commentId, request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "댓글이 정상적으로 삭제 되었습니다."));
    }

    //댓글 좋아요
    @PutMapping("/comment/{comment_id}/like")
    public ResponseEntity<SuccessResult> likeComment(@PathVariable("comment_id") String commentId) {
        commentService.increaseCommentLike(commentId);
        return ResponseEntity.ok().body(new SuccessResult("성공", "좋아요가 성공적으로 적용 되었습니다."));
    }
}
