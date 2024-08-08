package com.community.member.member.repository;

import com.community.member.member.domain.entity.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, String> {
    boolean existsByIndustryName(String industryName);
}