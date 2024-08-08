package com.community.domain.dto;

import com.community.domain.entity.Industry;
import com.community.domain.entity.PasswordQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ModifyInfoRequest {
    private String nickname;
    private String passwordQuestionId;
    private String findPasswordAnswer;
    private List<String> industriesId;
}
