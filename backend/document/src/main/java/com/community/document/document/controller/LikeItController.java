package com.community.document.document.controller;

import com.community.document.document.domain.dto.LikeViewershipDTO;
import com.community.document.document.service.LikeItService;
import com.community.document.global.dto.SuccessResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/document")
public class LikeItController {
	private LikeItService likeItService;

	@GetMapping({"/likeit/{uuid}"})
	public ResponseEntity<LikeViewershipDTO.LikeItResponse> searchLikeItCount(@PathVariable(name = "uuid") String uuid) {
		Long count = this.likeItService.showLikeItCount(uuid);
		return ResponseEntity.status(HttpStatus.OK).body(new LikeViewershipDTO.LikeItResponse(count));
	}

	@PutMapping({"/likeit/{uuid}"})
	public ResponseEntity<SuccessResult> updateLikeItCount(@PathVariable(name = "uuid") String uuid) {
		this.likeItService.increaseViewershipCount(uuid);
		return ResponseEntity.status(HttpStatus.OK).body(new SuccessResult("성공", "성공적으로 업데이트하였습니다."));
	}
}