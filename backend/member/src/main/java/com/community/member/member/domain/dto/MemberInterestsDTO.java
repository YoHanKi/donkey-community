package com.community.member.member.domain.dto;

import com.community.member.member.domain.entity.MemberInterests;
import lombok.Data;

public class MemberInterestsDTO {

    @Data
    public static class InterestResponse {
        private String industryId;
        private String industryName;

        public InterestResponse(MemberInterests interests) {
            industryId = interests.getIndustry().getIndustryId();
            industryName = interests.getIndustry().getIndustryName();
        }
    }
}
