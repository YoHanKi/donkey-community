package com.community.document.global.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedisObject {
    private String nickname;
    private String memberRoleName;
}