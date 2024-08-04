package com.community.controller;

import com.community.domain.dto.*;
import com.community.service.DocumentService;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class DocumentController {

    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    //slice 조회
    @GetMapping("/document/list/{board_id}")
    public ResponseEntity<List<FindDocumentResponse>> showAllDocument(
            @PathVariable("board_id") String boardId,
            @PageableDefault(size = 10) Pageable pageable) {
        List<FindDocumentResponse> list = documentService.findAllByBoard(boardId, pageable);
        return ResponseEntity.ok().body(list);
    }

    //단건 조회
    @GetMapping("/document/{document_id}")
    public ResponseEntity<FindDocumentResponse> showOneDocument(@PathVariable("document_id") String documentId) {
        FindDocumentResponse response = documentService.findOneDocument(documentId);
        return ResponseEntity.ok().body(response);
    }

    //게시글 작성
    @PostMapping("/document/manage")
    public ResponseEntity<SuccessResult> saveDocument(@RequestBody AddDocumentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        documentService.saveDocument(email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "게시글이 성공적으로 작성 되었습니다."));
    }

    //게시글 수정
    @PutMapping("/document/manage/{document_id}")
    public ResponseEntity<SuccessResult> modifyDocument(@PathVariable("document_id") String documentId,@RequestBody ModifyDocumentRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        documentService.modifyDocument(email,documentId,request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "게시글이 성공적으로 수정 되었습니다."));
    }


    //게시글 삭제
    @DeleteMapping("/document/manage/{document_id}")
    public ResponseEntity<SuccessResult> deleteDocument(@PathVariable("document_id") String documentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = Optional.ofNullable(authentication.getName()).orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        documentService.deleteDocument(email, documentId);
        return ResponseEntity.ok().body(new SuccessResult("성공", "게시글이 성공적으로 삭제 되었습니다."));
    }

    //게시글 좋아요
    @PutMapping("/document/{document_id}/like")
    public ResponseEntity<SuccessResult> increaseDocumentLike(@PathVariable("document_id") String documentId) {
        documentService.increaseDocumentLike(documentId);
        return ResponseEntity.ok().body(new SuccessResult("성공", "좋아요가 성공적으로 적용 되었습니다."));
    }

    //게시글 검색 조회
    @GetMapping("/document/search/")
    public ResponseEntity<List<FindDocumentResponse>> searchDocument(@RequestBody SearchDocumentRequest request,
                                                                     @PageableDefault(size = 10)
                                                                     @SortDefault.SortDefaults({@SortDefault(sort = "docCreateDate", direction = Sort.Direction.DESC)})
                                                                     Pageable pageable) {
        List<FindDocumentResponse> list = documentService.searchDocument(request.getKeyword(),pageable);
        return ResponseEntity.ok().body(list);
    }
}
