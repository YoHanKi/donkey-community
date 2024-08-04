package com.community.repository;

import com.community.domain.entity.LikeIt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeItRepository extends JpaRepository<LikeIt, String> {
    @Modifying
    @Query("UPDATE LikeIt SET likeCount = :count WHERE likeId = :likeId")
    void updateLikeCount(Long count, String likeId);
}
