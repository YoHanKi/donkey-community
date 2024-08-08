package com.community.document.document.controller;

import com.community.document.document.domain.dto.LikeViewershipDTO;
import com.community.document.document.service.ViewershipService;
import com.community.document.global.dto.SuccessResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class ViewershipController {
	private ViewershipService viewershipService;

	@GetMapping("/viewership/{doc_id}")
	public ResponseEntity<LikeViewershipDTO.ViewershipResponse> searchViewershipCount(@PathVariable(name="doc_id") String docId){
		Long count = viewershipService.showViewershipCount(docId);

		return ResponseEntity.status(HttpStatus.OK).body(new LikeViewershipDTO.ViewershipResponse(count));
	}


	@PutMapping("/viewership/{doc_id}")
	public ResponseEntity<SuccessResult> updateViewershipCount(@PathVariable(name="doc_id") String docId){
		viewershipService.increaseViewershipCount(docId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(new SuccessResult("성공", "성공적으로 업데이트하였습니다."));
	}
}
