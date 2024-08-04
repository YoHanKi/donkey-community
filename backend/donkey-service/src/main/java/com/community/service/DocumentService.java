package com.community.service;

import com.community.domain.dto.*;
import com.community.domain.entity.*;
import com.community.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DocumentService {

    private DocumentRepository documentRepository;
    private ViewershipRepository viewershipRepository;
    private LikeItRepository likeItRepository;
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;
    private CommentRepository commentRepository;

    public DocumentService(DocumentRepository documentRepository, ViewershipRepository viewershipRepository, LikeItRepository likeItRepository, BoardRepository boardRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        this.documentRepository = documentRepository;
        this.viewershipRepository = viewershipRepository;
        this.likeItRepository = likeItRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    //Slice 처리 조회(조회 시 조회수 함께 상승)
    @Transactional
    public List<FindDocumentResponse> findAllByBoard(String boardId, Pageable pageable) {
        Board board =boardRepository.findById(boardId).orElseThrow(() -> new EntityNotFoundException("해당 게시판에 게시글이 없습니다."));
        Slice<Document> slice = documentRepository.findAllByBoard(board, pageable);

        List<Document> list = slice.getContent();
        List<FindDocumentResponse> resultList = new ArrayList<>();

        for (Document document : list) {
            if (!document.getDocVisible()) continue;
            Long viewCount = viewershipRepository.findById(document.getDocId()).get().getViewCount() + 1L;
            Long likeCount = likeItRepository.findById(document.getDocId()).get().getLikeCount();

            viewershipRepository.updateViewership(viewCount, document.getDocId());

            resultList.add(new FindDocumentResponse(document, viewCount, likeCount));
        }
        return resultList;
    }

    //단건 조회
    public FindDocumentResponse findOneDocument(String documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(() -> new EntityNotFoundException("해당 게시물이 존재하지 않습니다."));
        Long viewCount = viewershipRepository.findById(documentId).get().getViewCount();
        Long likeCount = likeItRepository.findById(documentId).get().getLikeCount();

        return new FindDocumentResponse(document, viewCount, likeCount);
    }

    //게시글 작성
    @Transactional
    public DocumentWriteResponse saveDocument(String email, AddDocumentRequest request) {
        String uuid = UUID.randomUUID().toString();
        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("해당 사용자가 존재하지 않습니다."));
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(()-> new EntityNotFoundException("해당 게시판이 존재하지 않습니다."));
        if(request.getBoardId().isBlank() || request.getDocTitle().isEmpty() || request.getDocContent().isEmpty())
            throw new RuntimeException("빈칸이 존재합니다.");

        //LikeIt 테이블에 삽입
        LikeIt likeIt = LikeIt.builder()
            .likeId(uuid)
            .likeCount(0L)
            .build();

        //Viewership 테이블에 삽입
        Viewership viewership = Viewership.builder()
            .viewId(uuid)
            .viewCount(0L)
            .build();

        Document document = Document.builder()
                .docId(uuid)
                .docTitle(request.getDocTitle())
                .docContent(request.getDocContent())
                .docCreator(member)
                .board(board)
                .industry(board.getIndustry())
                .build();

        likeItRepository.save(likeIt);
        viewershipRepository.save(viewership);
        documentRepository.save(document);
        return new DocumentWriteResponse(document);
    }

    //게시글 수정
    @Transactional
    public DocumentWriteResponse modifyDocument(String email, String documentId, ModifyDocumentRequest request) {
        Member member = memberRepository.findByEmail(email).orElseThrow(()-> new EntityNotFoundException("해당 사용자가 존재하지 않습니다."));
        Document document = documentRepository.findById(documentId).orElseThrow(()->new EntityNotFoundException("게시글이 존재하지 않습니다."));

        if (document.getDocCreator().getEmail().equals(email)) {
            int count = 0;
            if(!(request.getDocTitle()==null || document.getDocTitle().equals(request.getDocTitle()))) {
                document.setDocTitle(request.getDocTitle());
                count++;
            }
            if(!(request.getDocContent()==null || document.getDocContent().equals(request.getDocContent()))) {
                document.setDocContent(request.getDocContent());
                count++;
            }
            if(count > 0) document.setDocModifier(member);
        }
        return new DocumentWriteResponse(document);
    }


    //게시글 삭제
    @Transactional
    public DocumentWriteResponse deleteDocument(String email, String documentId) {
        Document document =documentRepository.findById(documentId).orElseThrow(()->new EntityNotFoundException("게시글이 존재하지 않습니다."));
        if (document.getDocCreator().getEmail().equals(email)) {
            //게시글에 있던 댓글 모두 삭제
            try {
                if (commentRepository.findAllByDocument(document).isPresent()) {
                    commentRepository.deleteAllByDocument(document);
                }
            } catch (RuntimeException e) {}

            documentRepository.delete(document);
            return new DocumentWriteResponse(document);
        }
        throw new RuntimeException("작성자 외에 삭제할 수 없습니다.");
    }

    //게시글 좋아요
    @Transactional
    public DocumentWriteResponse increaseDocumentLike(String documentId) {
        Document document = documentRepository.findById(documentId).orElseThrow(()-> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        Long count = likeItRepository.findById(document.getDocId()).get().getLikeCount() + 1L;

        likeItRepository.updateLikeCount(count, document.getDocId());
        return new DocumentWriteResponse(document);
    }

    @Transactional
    public List<FindDocumentResponse> searchDocument(String keyword, Pageable pageable) {
        if (keyword == null) {
            throw new IllegalArgumentException("잘못된 입력 입니다.");
        }
        Slice<Document> slice = documentRepository.findByDocContentContaining(keyword,pageable);

        List<Document> list = slice.getContent();
        List<FindDocumentResponse> resultList = new ArrayList<>();

        for (Document document : list) {
            if (!document.getDocVisible()) continue;
            Long viewCount = viewershipRepository.findById(document.getDocId()).get().getViewCount() + 1L;
            Long likeCount = likeItRepository.findById(document.getDocId()).get().getLikeCount();

            viewershipRepository.updateViewership(viewCount, document.getDocId());

            resultList.add(new FindDocumentResponse(document, viewCount, likeCount));
        }
        return resultList;
    }
}
