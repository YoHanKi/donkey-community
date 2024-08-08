package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordQuestionResponse {
	private String passwordQuestionId;
	private String passwordQuestion;
}
