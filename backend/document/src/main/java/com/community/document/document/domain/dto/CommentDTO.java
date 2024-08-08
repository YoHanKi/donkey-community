package com.community.document.document.domain.dto;

import com.community.document.document.domain.entity.Comment;
import com.community.document.document.domain.entity.Document;
import com.community.document.global.dto.RedisObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class CommentDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindCommentResponse {
        private String commentId;
        private String commentCreatorIp;
        private String commentContent;
        private String commentDate;

        private String nickname;
        private String email;
        private String memberRoleName;

        private String docId;

        private Long likeCount;

        public FindCommentResponse(Comment comment,RedisObject redisObject, Long likeCount) {
            commentId = comment.getCommentId();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss.SSS");
            commentDate = comment.getCommentCreateDate().format(formatter);
            commentContent = comment.getCommentContent();
            if (comment.getCommentCreatorEmail() == null) {
                String[] tmp;
                if(comment.getCommentCreatorIp().contains(".")) {
                    tmp = comment.getCommentCreatorIp().split("\\.");
                } else {
                    tmp = comment.getCommentCreatorIp().split(":");
                }
                commentCreatorIp = tmp[0] + "." + tmp[1] + ".*.*";
                nickname = comment.getAnonyNickname();
            } else {
                nickname = redisObject.getNickname();
                String[] tmp = comment.getCommentCreatorEmail().split("@");
                email = tmp[0];
                memberRoleName = redisObject.getMemberRoleName();
            }
            docId = comment.getDocument().getDocId();
            this.likeCount = likeCount;
        }
    }

    @Data
    @AllArgsConstructor
    public static class AddCommentRequest {

        private String commentPassword;

        private String commentCreatorIp;

        private String commentContent;

        private String anonyNickname;
    }

    @Data
    @AllArgsConstructor
    public static class CommentCommonResponse {
        private String commentId;
        private String commentContent;

        public CommentCommonResponse(Comment comment) {
            commentId = comment.getCommentId();
            commentContent = comment.getCommentContent();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ModifyCommentRequest {
        private String commentPassword;
        private String commentContent;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteCommentRequest {
        private String commentPassword;
    }
}
