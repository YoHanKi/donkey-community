package com.community.domain.dto;

import com.community.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindCommentResponse {
    private String commentId;
    private String commentCreatorIp;
    private String commentContent;
    private String commentDate;

    private String nickname;
    private String email;
    private String memberRoleName;

    private String docId;

    private Long likeCount;

    public FindCommentResponse(Comment comment, Long likeCount) {
        commentId = comment.getCommentId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss.SSS");
        commentDate = comment.getCommentCreateDate().format(formatter);
        commentContent = comment.getCommentContent();
        if (comment.getCommentCreator() == null) {
            String[] tmp;
            if(comment.getCommentCreatorIp().contains(".")) {
                tmp = comment.getCommentCreatorIp().split("\\.");
            } else {
                tmp = comment.getCommentCreatorIp().split(":");
            }
            commentCreatorIp = tmp[0] + "." + tmp[1] + ".*.*";
            nickname = comment.getAnonyNickname();
        } else {
            nickname = comment.getCommentCreator().getNickname();
            String[] tmp = comment.getCommentCreator().getEmail().split("@");
            email = tmp[0];
            memberRoleName = comment.getCommentCreator().getMemberRole().getMemberRoleName();
        }
        docId = comment.getDocument().getDocId();
        this.likeCount = likeCount;
    }
}
