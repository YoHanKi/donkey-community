package com.community.document.document.service;

import com.community.document.document.domain.entity.LikeIt;
import com.community.document.document.repository.LikeItRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeItService {
	private final LikeItRepository likeItRepository;
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
