package com.community.domain.dto;

import com.community.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentCommonResponse {
    private String commentId;

    private String commentContent;

    public CommentCommonResponse(Comment comment) {
        commentId = comment.getCommentId();
        commentContent = comment.getCommentContent();
    }
}
