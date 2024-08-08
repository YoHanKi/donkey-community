package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class IndustryResponse {
    private String industryId;
    private String industryName;
    private String industryDescription;

    public IndustryResponse(String industryId, String industryName){
       industryId = industryId;
       industryName = industryName;
       industryDescription = "";
    }
}
