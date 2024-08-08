package com.community.repository;

import com.community.domain.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies, String> {
    Optional<Companies> findByComName(String comName);

    boolean existsByComId(String comId);
}
