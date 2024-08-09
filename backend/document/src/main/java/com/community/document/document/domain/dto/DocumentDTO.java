package com.community.document.document.domain.dto;

import com.community.document.document.domain.entity.Document;
import com.community.document.global.dto.RedisObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

public class DocumentDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindDocumentResponse {
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

        public FindDocumentResponse(Document document, RedisObject redisObject, Long viewCount, Long likeCount) {
            docId = document.getDocId();
            docTitle = document.getDocTitle();
            docContent = document.getDocContent();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss.SSS");
            docCreateDate = document.getDocCreateDate().format(formatter);

            nickname = redisObject.getNickname();
            String[] tmp = document.getDocCreatorEmail().split("@");
            email = tmp[0];
            memberRoleName = redisObject.getMemberRoleName();

            boardId = document.getBoard().getBoardId();
            boardName = document.getBoard().getBoardName();

            this.likeCount = likeCount;
            this.viewCount = viewCount;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DocumentWriteResponse {
        private String docId;
        private String docTitle;
        private String docContent;
        private String boardId;
        private String boardName;

        public DocumentWriteResponse(Document document) {
            docId = document.getDocId();
            docTitle = document.getDocTitle();
            docContent = document.getDocContent();
            boardId = document.getBoard().getBoardId();
            boardName = document.getBoard().getBoardName();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddDocumentRequest {
        private String docTitle;
        private String docContent;
        private String boardId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModifyDocumentRequest {
        private String docTitle;
        private String docContent;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SearchDocumentRequest {
        private String keyword;
    }

}
