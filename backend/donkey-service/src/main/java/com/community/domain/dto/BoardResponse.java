package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardResponse {
	private String boardId;
	private String boardName;
	private String comName;
	private String industryName;
}