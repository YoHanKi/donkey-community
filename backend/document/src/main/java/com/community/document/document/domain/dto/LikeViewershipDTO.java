package com.community.document.document.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class LikeViewershipDTO {
    @Data
    @AllArgsConstructor
    public static class LikeItResponse {
        private Long likeCount;
    }
    @Data
    @AllArgsConstructor
    public static class ViewershipResponse {
        private Long viewCount;
    }
}
