package com.community.document.document.controller;

import com.community.document.document.domain.dto.DocumentDTO;
import com.community.document.document.service.DocumentService;
import com.community.document.global.dto.SuccessResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;


    //slice 조회
    @GetMapping("/list/{board_id}")
    public ResponseEntity<List<DocumentDTO.FindDocumentResponse>> showAllDocument(
            @PathVariable("board_id") String boardId,
            @SortDefault.SortDefaults({@SortDefault(sort = "docCreateDate", direction = Sort.Direction.DESC)})
            @PageableDefault(size = 10) Pageable pageable) {
        List<DocumentDTO.FindDocumentResponse> list = documentService.findAllByBoard(boardId, pageable);
        return ResponseEntity.ok().body(list);
    }

    //단건 조회
    @GetMapping("/{document_id}")
    public ResponseEntity<DocumentDTO.FindDocumentResponse> showOneDocument(@PathVariable("document_id") String documentId) {
        DocumentDTO.FindDocumentResponse response = documentService.findOneDocument(documentId);
        return ResponseEntity.ok().body(response);
    }

    //게시글 작성
    @PostMapping("/manage")
    public ResponseEntity<SuccessResult> saveDocument(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @RequestBody DocumentDTO.AddDocumentRequest request) {
        documentService.saveDocument(userEmail, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResult("성공", "게시글이 성공적으로 작성 되었습니다."));
    }

    //게시글 수정
    @PutMapping("/manage/{document_id}")
    public ResponseEntity<SuccessResult> modifyDocument(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PathVariable("document_id") String documentId,@RequestBody DocumentDTO.ModifyDocumentRequest request) {
        documentService.modifyDocument(userEmail,documentId,request);
        return ResponseEntity.ok().body(new SuccessResult("성공", "게시글이 성공적으로 수정 되었습니다."));
    }


    //게시글 삭제
    @DeleteMapping("/manage/{document_id}")
    public ResponseEntity<SuccessResult> deleteDocument(
            @RequestHeader(value = "u-Email", defaultValue = "") String userEmail,
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @PathVariable("document_id") String documentId) {
        documentService.deleteDocument(userEmail, documentId);
        return ResponseEntity.ok().body(new SuccessResult("성공", "게시글이 성공적으로 삭제 되었습니다."));
    }

    //게시글 좋아요
    @PutMapping("/{document_id}/like")
    public ResponseEntity<SuccessResult> increaseDocumentLike(@PathVariable("document_id") String documentId) {
        documentService.increaseDocumentLike(documentId);
        return ResponseEntity.ok().body(new SuccessResult("성공", "좋아요가 성공적으로 적용 되었습니다."));
    }

    //게시글 검색 조회
    @GetMapping("/search/")
    public ResponseEntity<List<DocumentDTO.FindDocumentResponse>> searchDocument(@RequestBody DocumentDTO.SearchDocumentRequest request,
                                                                                 @PageableDefault(size = 10)
                                                                     @SortDefault.SortDefaults({@SortDefault(sort = "docCreateDate", direction = Sort.Direction.DESC)})
                                                                     Pageable pageable) {
        List<DocumentDTO.FindDocumentResponse> list = documentService.searchDocument(request.getKeyword(),pageable);
        return ResponseEntity.ok().body(list);
    }
}