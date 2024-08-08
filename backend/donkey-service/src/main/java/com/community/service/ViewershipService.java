package com.community.service;

import org.springframework.stereotype.Service;

import com.community.domain.entity.Viewership;
import com.community.repository.ViewershipRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ViewershipService {
	private ViewershipRepository viewershipRepository;

	@Transactional
	public void increaseViewershipCount(String docId){
		 Viewership viewership = viewershipRepository.findById(docId).orElseThrow(() -> new IllegalArgumentException("잘못된 id를 입력하였습니다."));

		 viewershipRepository.updateViewership(viewership.getViewCount() + 1L, docId);
	}

	public Long showViewershipCount(String docId) {
		Viewership viewership = viewershipRepository.findById(docId).orElseThrow(() -> new IllegalArgumentException("잘못된 id를 입력하였습니다."));

		return viewership.getViewCount();
	}
}
