package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateBoardRequest {
    private String boardName;
    private String industryId;
    private String comId;
    private String requesterId;
}
