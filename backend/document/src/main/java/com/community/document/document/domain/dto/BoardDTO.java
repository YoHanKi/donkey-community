package com.community.document.document.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class BoardDTO {
    @Data
    @AllArgsConstructor
    public static class CreateBoardRequest {
        private String boardName;
        private String industryId;
        private String comId;
        private String requesterEmail;

        public String getComId(){
            return this.comId == null ? "" : this.comId;
        }
    }

    @Data
    @AllArgsConstructor
    public static class UpdateBoardApproveRequest {
        private String boardId;
        private Boolean approval;
    }

    @Data
    @AllArgsConstructor
    public static class BoardResponse {
        private String boardId;
        private String boardName;
        private String comName;
        private String industryName;
    }
}
