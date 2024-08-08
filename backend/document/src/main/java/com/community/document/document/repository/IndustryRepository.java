package com.community.document.document.repository;

import com.community.document.document.domain.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, String> {
    boolean existsByIndustryName(String industryName);
}
