package com.community.controller;

import com.community.domain.dto.LikeItResponse;
import com.community.domain.dto.SuccessResult;
import com.community.service.LikeItService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class LikeItController {
	private LikeItService likeItService;

	@GetMapping({"/likeit/{uuid}"})
	public ResponseEntity<LikeItResponse> searchLikeItCount(@PathVariable(name = "uuid") String uuid) {
		Long count = this.likeItService.showLikeItCount(uuid);
		return ResponseEntity.status(HttpStatus.OK).body(new LikeItResponse(count));
	}

	@PutMapping({"/likeit/{uuid}"})
	public ResponseEntity<SuccessResult> updateLikeItCount(@PathVariable(name = "uuid") String uuid) {
		this.likeItService.increaseViewershipCount(uuid);

		return ResponseEntity.status(HttpStatus.OK).body(new SuccessResult("성공", "성공적으로 업데이트하였습니다."));
	}
}