package com.community.domain.dto;

import com.community.domain.entity.PasswordQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordRequest {
    private String email;
    private String passwordQuestionId;
    private String findPasswordAnswer;
}
