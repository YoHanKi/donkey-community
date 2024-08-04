package com.community.domain.dto;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String nickname;
    private String email;
    private List<InterestResponse> interest;

    public UserInfoResponse(Member member, List<InterestResponse> list) {
        nickname = member.getNickname();
        email = member.getEmail();
        interest = list;
    }
}
