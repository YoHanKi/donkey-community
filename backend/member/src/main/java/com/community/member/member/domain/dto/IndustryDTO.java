package com.community.member.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class IndustryDTO {
    @Data
    @AllArgsConstructor
    public static class AddIndustryRequest {
        private String industryName;
        private String industryComment;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class IndustryResponse {
        private String industryId;
        private String industryName;
        private String industryDescription;
    }
}
