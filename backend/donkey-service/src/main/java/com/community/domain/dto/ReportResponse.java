package com.community.domain.dto;

import com.community.domain.entity.Member;
import com.community.domain.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private String reporterIp;
    private String reportContent;
    private String reporterId;
    private String reportDate;


    public ReportResponse(Report report) {
        reporterIp = report.getReporterIp();
        reportContent = report.getReportContent();
        String[] tmp = report.getReporter().getEmail().split("@");
        reporterId = tmp[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss.SSS");
        reportDate = report.getReportDate().format(formatter);
    }
}
