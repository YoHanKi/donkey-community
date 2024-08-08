package com.community.member.member.controller;

import com.community.member.global.dto.ErrorResult;
import com.community.member.member.domain.dto.MemberDTO;
import com.community.member.member.service.PasswordQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class PasswordQuestionController {
	private final PasswordQuestionService passwordQuestionService;

	@GetMapping("/passwordquestion")
	public ResponseEntity<List<MemberDTO.PasswordQuestionResponse>> searchPasswordQuestion(){
		List<MemberDTO.PasswordQuestionResponse> resultList = passwordQuestionService.showAllPasswordQuestion();
		return ResponseEntity.status(HttpStatus.OK)
			.body(resultList);
	}

	@PostMapping("/admin/passwordquestion")
	public ResponseEntity<ErrorResult> savePasswordQuestion(@RequestBody MemberDTO.AddPasswordQuestionRequest request){
		passwordQuestionService.savePasswordQuestion(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ErrorResult("성공", "정상적으로 추가되었습니다."));
	}
}