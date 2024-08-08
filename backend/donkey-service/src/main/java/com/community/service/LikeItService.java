package com.community.service;

import org.springframework.stereotype.Service;

import com.community.domain.entity.LikeIt;
import com.community.repository.LikeItRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class LikeItService {
	private LikeItRepository likeItRepository;
	public Long showLikeItCount(String uuid) {
		LikeIt likeIt = likeItRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("유일하지 않은 id입니다."));

		return likeIt.getLikeCount();
	}

	@Transactional
	public void increaseViewershipCount(String uuid) {
		LikeIt likeIt = likeItRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("유일하지 않은 id입니다."));

		likeItRepository.updateLikeCount(likeIt.getLikeCount() + 1L, uuid);
	}
}
