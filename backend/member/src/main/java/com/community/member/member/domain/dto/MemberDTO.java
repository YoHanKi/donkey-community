package com.community.member.member.domain.dto;

import com.community.member.member.domain.entity.Member;
import lombok.*;

import java.util.List;

public class MemberDTO {

    @Data
    @AllArgsConstructor
    public static class AddMemberRequest {
        private String name;
        private String nickname;
        private String email;
        private String password;
        private String gender;
        private String phone;
        private Long passwordQuestionId;
        private String findPasswordAnswer;
        private List<String> industries;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberResponse {
        private String nickname;
        private String email;

        public MemberResponse(Member member) {
            nickname = member.getNickname();
            email = member.getEmail();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ModifyInfoRequest {
        private String nickname;
        private Long passwordQuestionId;
        private String findPasswordAnswer;
        private List<String> industriesId;
    }

    @Data
    @AllArgsConstructor
    public static class WithdrawalRequest {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoResponse {
        private String nickname;
        private String email;
        private List<MemberInterestsDTO.InterestResponse> interest;

        public UserInfoResponse(Member member, List<MemberInterestsDTO.InterestResponse> list) {
            nickname = member.getNickname();
            email = member.getEmail();
            interest = list;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindPasswordResponse {
        private String email;

        public FindPasswordResponse(FindPasswordRequest request) {
            email = request.getEmail();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindPasswordRequest {
        private String email;
        private Long passwordQuestionId;
        private String findPasswordAnswer;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ChangePasswordRequest {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddPasswordQuestionRequest {
        private String question;
    }

    @Data
    @AllArgsConstructor
    public static class PasswordQuestionResponse {
        private Long passwordQuestionId;
        private String passwordQuestion;
    }
}
