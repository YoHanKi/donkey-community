package com.community.document.document.service;

import com.community.document.document.domain.dto.CommentDTO;
import com.community.document.document.domain.entity.Comment;
import com.community.document.document.domain.entity.Document;
import com.community.document.document.domain.entity.LikeIt;
import com.community.document.document.repository.CommentRepository;
import com.community.document.document.repository.DocumentRepository;
import com.community.document.document.repository.LikeItRepository;
import com.community.document.global.dto.RedisObject;
import com.community.document.global.util.RedisUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DocumentRepository documentRepository;
    private final LikeItRepository likeItRepository;
    private final RedisUtil redisUtil;


    //댓글 단건 조회
    public CommentDTO.FindCommentResponse findOneById(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글 입니다."));
        Long likeCount = likeItRepository.findById(commentId).get().getLikeCount();
        RedisObject redisObject = redisUtil.getDocumentData(comment.getCommentCreatorEmail());
        return new CommentDTO.FindCommentResponse(comment,redisObject, likeCount);
    }

    //댓글 모두 조회
    public List<CommentDTO.FindCommentResponse> findAllByDocs(String docId) {
        Document document = documentRepository.findById(docId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        List<Comment> list = commentRepository.findAllByDocument(document).orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        List<CommentDTO.FindCommentResponse> response = new ArrayList<>();
        for (Comment comment : list) {
            if(!comment.getCommentVisible()) continue;
            LikeIt likeIt = likeItRepository.findById(docId).orElse(likeItRepository.save(new LikeIt(UUID.randomUUID().toString(), 0L)));
            Long likeCount = likeIt.getLikeCount();
            RedisObject redisObject = redisUtil.getDocumentData(comment.getCommentCreatorEmail());
            response.add(new CommentDTO.FindCommentResponse(comment, redisObject, likeCount));
        }

        return response;
    }

    //댓글 생성
    @Transactional
    public CommentDTO.CommentCommonResponse saveComment(String email, String docId, CommentDTO.AddCommentRequest request) {
        //document ID 추가
        Document document = documentRepository.findById(docId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        //UUID 생성
        String uuid = UUID.randomUUID().toString();

        RedisObject redisObject = redisUtil.getDocumentData(email);

        //LikeIt 테이블에 삽입
        LikeIt likeIt = LikeIt.builder()
                .likeId(uuid)
                .likeCount(0L)
                .build();

        Comment comment = Comment.builder()
                .commentId(uuid)
                .commentPassword(request.getCommentPassword())
                .commentCreatorIp(request.getCommentCreatorIp())
                .commentContent(request.getCommentContent())
                .document(document)
                .commentCreatorEmail(email)
                .anonyNickname(redisObject.getNickname() == null ? request.getAnonyNickname() : null)
                .build();

        likeItRepository.save(likeIt);
        return new CommentDTO.CommentCommonResponse(commentRepository.save(comment));
    }

    @Transactional
    public CommentDTO.CommentCommonResponse updateComment(String email, String comment_id, CommentDTO.ModifyCommentRequest request) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!email.isEmpty() && comment.getCommentCreatorEmail() != null) {
            if (!(comment.getCommentCreatorEmail().equals(email))) throw new RuntimeException("작성자 외에 수정할 수 없습니다.");
        }

        if(comment.getCommentPassword() != null) {
            if (comment.getCommentPassword().equals(request.getCommentPassword()))
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if(!(request.getCommentContent() == null || comment.getCommentContent().equals(request.getCommentContent()))) {
            comment.setCommentContent(request.getCommentContent());
        }

        comment.setCommentModifierEmail(email);

        return new CommentDTO.CommentCommonResponse(comment);
    }

    @Transactional
    public CommentDTO.CommentCommonResponse deleteComment(String email, String commentId, CommentDTO.DeleteCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (!email.isEmpty() && comment.getCommentCreatorEmail() != null) {
            if (!(comment.getCommentCreatorEmail().equals(email))) throw new RuntimeException("작성자 외에 수정할 수 없습니다.");
        }

        if(comment.getCommentPassword() != null) {
            if (comment.getCommentPassword().equals(request.getCommentPassword()))
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        commentRepository.delete(comment);
        likeItRepository.deleteById(commentId);

        return new CommentDTO.CommentCommonResponse(comment);
    }


    //댓글 좋아요 증가
    @Transactional
    public CommentDTO.CommentCommonResponse increaseCommentLike(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));
        Long count = likeItRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다.")).getLikeCount() + 1L;

        likeItRepository.updateLikeCount(count, commentId);
        return new CommentDTO.CommentCommonResponse(comment);
    }
}