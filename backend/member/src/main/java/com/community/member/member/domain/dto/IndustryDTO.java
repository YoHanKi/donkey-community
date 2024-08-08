package com.community.member.member.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class IndustryDTO {
    @Data
    @AllArgsConstructor
    public static class AddIndustryRequest {
        private String industryName;
        private String industryComment;
    }
}
