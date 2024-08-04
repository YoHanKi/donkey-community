package com.community.domain.dto;

import com.community.domain.entity.MemberInterests;
import lombok.Data;

@Data
public class InterestResponse {
    private String industryId;
    private String industryName;

    public InterestResponse(MemberInterests interests) {
        industryId = interests.getIndustry().getIndustryId();
        industryName = interests.getIndustry().getIndustryName();
    }
}
