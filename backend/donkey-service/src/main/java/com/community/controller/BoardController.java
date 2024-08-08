package com.community.controller;

import java.util.List;

import com.community.domain.dto.BoardResponse;
import com.community.domain.dto.CreateBoardRequest;
import com.community.domain.dto.ErrorResult;
import com.community.domain.dto.SuccessResult;
import com.community.service.BoardService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<SuccessResult> requestCreateBoard(@RequestBody CreateBoardRequest body){
        boardService.saveBoard(body);
        SuccessResult result = new SuccessResult("성공", "성공적으로 생성 요청을 완료하였습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/board/{isApprove}")
    public ResponseEntity<List<BoardResponse>> searchBoard(@PathVariable Boolean isApprove){
        List<BoardResponse> resultList = boardService.showAllBoardByCondition(isApprove);
        return ResponseEntity.status(HttpStatus.OK)
            .body(resultList);
    }
}
