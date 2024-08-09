package com.community.document.admin.repository;

import com.community.document.admin.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    Optional<Report> findReportByReporterIpAndReportThing(String ip, String thing);
    int countAllByReportThing(String thing);
}
