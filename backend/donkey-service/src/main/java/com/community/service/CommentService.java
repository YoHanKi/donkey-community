package com.community.service;

import com.community.domain.dto.*;
import com.community.domain.entity.Comment;
import com.community.domain.entity.Document;
import com.community.domain.entity.LikeIt;
import com.community.domain.entity.Member;
import com.community.repository.CommentRepository;
import com.community.repository.DocumentRepository;
import com.community.repository.LikeItRepository;
import com.community.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private CommentRepository commentRepository;
    private DocumentRepository documentRepository;
    private LikeItRepository likeItRepository;
    private MemberRepository memberRepository;

    public CommentService(CommentRepository commentRepository, DocumentRepository documentRepository, LikeItRepository likeItRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.documentRepository = documentRepository;
        this.likeItRepository = likeItRepository;
        this.memberRepository = memberRepository;
    }

    //댓글 단건 조회
    public FindCommentResponse findOneById(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 댓글 입니다."));
        Long likeCount = likeItRepository.findById(commentId).get().getLikeCount();
        return new FindCommentResponse(comment, likeCount);
    }

    //댓글 모두 조회
    public List<FindCommentResponse> findAllByDocs(String docId) {
        Document document = documentRepository.findById(docId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        List<Comment> list = commentRepository.findAllByDocument(document).orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        List<FindCommentResponse> response = new ArrayList<>();
        for (Comment comment : list) {
            if(!comment.getCommentVisible()) continue;
            LikeIt likeIt = likeItRepository.findById(docId).orElse(likeItRepository.save(new LikeIt(UUID.randomUUID().toString(), 0L)));
            Long likeCount = likeIt.getLikeCount();
            response.add(new FindCommentResponse(comment, likeCount));
        }

        return response;
    }

    //댓글 생성
    @Transactional
    public CommentCommonResponse saveComment(String email,String docId, AddCommentRequest request) {
        //document ID 추가
        Document document = documentRepository.findById(docId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        //UUID 생성
        String uuid = UUID.randomUUID().toString();
        //멤버 ID 추가
        Member member = null;
        if(!email.isEmpty()) member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("잘못된 사용자 입니다."));

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
                .commentCreator(member == null? null : member)
                .anonyNickname(member == null ? request.getAnonyNickname() : null)
                .build();

        likeItRepository.save(likeIt);
        return new CommentCommonResponse(commentRepository.save(comment));
    }

    @Transactional
    public CommentCommonResponse updateComment(String email, String comment_id, ModifyCommentRequest request) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        Member member = null;
        if(!email.isEmpty()) member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("잘못된 사용자 입니다."));

        if (!email.isEmpty() && comment.getCommentCreator().getEmail() != null) {
            if (!(comment.getCommentCreator().getMemberId().equals(member.getMemberId()))) throw new RuntimeException("작성자 외에 수정할 수 없습니다.");
        }

        if(comment.getCommentPassword() != null) {
            if (comment.getCommentPassword().equals(request.getCommentPassword()))
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if(!(request.getCommentContent() == null || comment.getCommentContent().equals(request.getCommentContent()))) {
            comment.setCommentContent(request.getCommentContent());
        }

        if(member != null) comment.setCommentModifier(member);

        return new CommentCommonResponse(comment);
    }

    @Transactional
    public CommentCommonResponse deleteComment(String email, String commentId, DeleteCommentRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));

        Member member = null;
        if(!email.isEmpty()) member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("잘못된 사용자 입니다."));

        if (!email.isEmpty() && comment.getCommentCreator().getEmail() != null) {
            if (!(comment.getCommentCreator().getMemberId().equals(member.getMemberId()))) throw new RuntimeException("작성자 외에 삭제할 수 없습니다.");
        }

        if(comment.getCommentPassword() != null) {
            if (comment.getCommentPassword().equals(request.getCommentPassword()))
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        commentRepository.delete(comment);
        likeItRepository.deleteById(commentId);

        return new CommentCommonResponse(comment);
    }


    //댓글 좋아요 증가
    @Transactional
    public CommentCommonResponse increaseCommentLike(String commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다."));
        Long count = likeItRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException("해당 댓글이 존재하지 않습니다.")).getLikeCount() + 1L;

        likeItRepository.updateLikeCount(count, commentId);
        return new CommentCommonResponse(comment);
    }
}
