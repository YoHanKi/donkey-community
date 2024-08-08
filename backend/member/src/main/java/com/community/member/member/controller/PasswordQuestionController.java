package com.community.member.member.controller;

import com.community.member.member.domain.dto.MemberDTO;
import com.community.member.member.service.PasswordQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}