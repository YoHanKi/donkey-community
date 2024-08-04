package com.community.repository;

import com.community.domain.entity.ReviewScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewScoreRepository extends JpaRepository<ReviewScore, String> {
}
