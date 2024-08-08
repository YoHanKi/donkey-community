package com.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.community.domain.dto.PasswordQuestionResponse;
import com.community.service.PasswordQuestionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PasswordQuestionController {
	private PasswordQuestionService passwordQuestionService;


	@GetMapping("/passwordquestion")
	public ResponseEntity<List<PasswordQuestionResponse>> searchPasswordQuestion(){
		List<PasswordQuestionResponse> resultList = passwordQuestionService.showAllPasswordQuestion();

		return ResponseEntity.status(HttpStatus.OK)
			.body(resultList);
	}
}
