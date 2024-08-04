package com.community.domain.dto;

import com.community.domain.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindDocumentResponse {
    private String docId;
    private String docTitle;
    private String docContent;
    private String docCreateDate;

    private String nickname;
    private String email;
    private String memberRoleName;

    private String boardId;
    private String boardName;

    private Long likeCount;
    private Long viewCount;

    public FindDocumentResponse(Document document, Long viewCount, Long likeCount) {
        docId = document.getDocId();
        docTitle = document.getDocTitle();
        docContent = document.getDocContent();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss.SSS");
        docCreateDate = document.getDocCreateDate().format(formatter);

        nickname = document.getDocCreator().getNickname();
        String[] tmp = document.getDocCreator().getEmail().split("@");
        email = tmp[0];
        memberRoleName = document.getDocCreator().getMemberRole().getMemberRoleName();

        boardId = document.getBoard().getBoardId();
        boardName = document.getBoard().getBoardName();

        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }
}
