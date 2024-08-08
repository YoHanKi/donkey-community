package com.community.repository;

import com.community.domain.entity.Viewership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewershipRepository extends JpaRepository<Viewership, String> {
    @Modifying
    @Query("UPDATE Viewership SET viewCount = :count WHERE viewId = :viewId")
    void updateViewership(Long count, String viewId);
}
