package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBoardApproveRequest {
	private String boardId;
	private Boolean approval;
}
