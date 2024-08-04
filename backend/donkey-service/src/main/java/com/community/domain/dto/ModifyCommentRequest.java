package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyCommentRequest {

    private String commentPassword;

    private String commentContent;
}
