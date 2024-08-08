package com.community.document.document.repository;

import com.community.document.document.domain.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies, String> {
    Optional<Companies> findByComName(String comName);

    boolean existsByComId(String comId);
}
