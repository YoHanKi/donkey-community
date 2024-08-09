package com.community.document.document.controller;

import com.community.document.document.domain.dto.BoardDTO;
import com.community.document.document.service.BoardService;
import com.community.document.global.dto.ErrorResult;
import com.community.document.global.dto.SuccessResult;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<SuccessResult> requestCreateBoard(@RequestBody BoardDTO.CreateBoardRequest body){
        boardService.saveBoard(body);
        SuccessResult result = new SuccessResult("성공", "성공적으로 생성 요청을 완료하였습니다.");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/board/{isApprove}")
    public ResponseEntity<List<BoardDTO.BoardResponse>> searchBoard(@PathVariable Boolean isApprove){
        List<BoardDTO.BoardResponse> resultList = boardService.showAllBoardByCondition(isApprove);
        return ResponseEntity.status(HttpStatus.OK)
                .body(resultList);
    }

    @PutMapping("/admin/board")
    public ResponseEntity<ErrorResult> updateBoardStatus(
            @RequestHeader(value = "u-Role", defaultValue = "ANONYMOUS") String userRole,
            @RequestBody BoardDTO.UpdateBoardApproveRequest request){
        if(!userRole.equals("ADMIN")) {
            throw new IllegalArgumentException("잘못된 접근입니다. 권한이 없습니다.");
        }
        boardService.updateApprove(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorResult("성공", "정상적으로 수정하였습니다."));
    }
}
