package com.community.document.admin.domain.dto;

import com.community.document.admin.domain.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class ReportDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportResponse {
        private String reporterIp;
        private String reportContent;
        private String reporterId;
        private String reportDate;


        public ReportResponse(Report report) {
            reporterIp = report.getReporterIp();
            reportContent = report.getReportContent();
            String[] tmp = report.getReporterEmail().split("@");
            reporterId = tmp[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss.SSS");
            reportDate = report.getReportDate().format(formatter);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportRequest {
        private String reportContent;
    }
}
