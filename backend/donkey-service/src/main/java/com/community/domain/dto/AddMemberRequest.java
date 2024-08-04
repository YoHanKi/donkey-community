package com.community.domain.dto;

import com.community.domain.entity.Industry;
import com.community.domain.entity.PasswordQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@AllArgsConstructor
public class AddMemberRequest {
    private String name;
    private String nickname;
    private String email;
    private String password;
    private String gender;
    private String phone;
    private String passwordQuestionId;
    private String findPasswordAnswer;
    private List<String> industries;
}
