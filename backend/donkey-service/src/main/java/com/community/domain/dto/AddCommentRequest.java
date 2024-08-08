package com.community.domain.dto;

import com.community.domain.entity.Document;
import com.community.domain.entity.LikeIt;
import com.community.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCommentRequest {

    private String commentPassword;

    private String commentCreatorIp;

    private String commentContent;

    private String anonyNickname;
}
