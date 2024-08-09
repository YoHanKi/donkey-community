package com.community.document.document.controller;

import com.community.document.document.domain.dto.CommentDTO;
import com.community.document.document.service.CommentService;
import com.community.document.global.dto.SuccessResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    // 댓글 리스트 가져오기
    @GetMapping("/comment/list/{doc_id}")
    public ResponseEntity<List<CommentDTO.FindCommentResponse>> showCommentAll(@PathVariable("doc_id") String docId) {
        List<CommentDTO.FindCommentResponse> list = commentService.findAllByDocs(docId);
        return ResponseEntity.ok().body(list);
    }

    // 댓글 단건 조회
    @GetMapping("/comment/{comment_id}")
    public ResponseEntity<CommentDTO.FindCommentResponse> showOneComment(@PathVariable("comment_id") String commentId) {
        return ResponseEntity.ok().body(commentService.findOneById(commentId));
    }

    //댓글 생성
    @PostMapping("/comment/{doc_id}")
    public ResponseEntity<SuccessResult> saveComment(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PathVariable("doc_id") String docId, @RequestBody CommentDTO.AddCommentRequest request, HttpServletRequest servletRequest) {
        //아이피 가져오기
        request.setCommentCreatorIp(servletRequest.getRemoteAddr());
        //접속 중인 사용자의 데이터 가져오기
        String email = "";
        if (!userRole.equals("ANONYMOUS")) email = userEmail;
        commentService.saveComment(email ,docId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "댓글이 정상적으로 등록 되었습니다."));
    }

    //댓글 수정
    @PutMapping("/comment/{comment_id}")
    public ResponseEntity<SuccessResult> modifyComment(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PathVariable("comment_id") String commentId, @RequestBody CommentDTO.ModifyCommentRequest request) {
        String email = "";
        if (!userRole.equals("ANONYMOUS")) email = userEmail;
        commentService.updateComment(email, commentId, request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "댓글이 정상적으로 수정 되었습니다."));
    }

    //댓글 삭제
    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<SuccessResult> deleteComment(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PathVariable("comment_id") String commentId, @RequestBody CommentDTO.DeleteCommentRequest request) {
        String email = "";
        if (!userRole.equals("ANONYMOUS")) email = userEmail;
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