package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class AddIndustryRequest {
    private String industryName;
    private String industryComment;

    public AddIndustryRequest(String industryName){
        this.industryName = industryName;
    }
}
