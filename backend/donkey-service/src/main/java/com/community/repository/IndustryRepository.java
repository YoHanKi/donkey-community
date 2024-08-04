package com.community.repository;

import com.community.domain.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, String> {
    boolean existsByIndustryName(String industryName);
}
