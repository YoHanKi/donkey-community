package com.community.domain.dto;

import com.community.domain.entity.Member;
import com.community.domain.entity.PasswordQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private String nickname;
    private String email;

    public MemberResponse(Member member) {
        nickname = member.getNickname();
        email = member.getEmail();
    }
}
