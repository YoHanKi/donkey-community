package com.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @Column(name = "comment_id")
    private String commentId;

    @Column(name = "comment_password")
    private String commentPassword;

    @Column(name = "comment_creator_ip")
    private String commentCreatorIp;

    @Column(name = "comment_content", nullable = false)
    private String commentContent;

    @Column(name = "anony_nickname")
    private String anonyNickname;

    @CreatedDate
    @Column(name = "comment_create_date")
    private LocalDateTime commentCreateDate;

    @LastModifiedDate
    @Column(name = "comment_mod_date")
    private LocalDateTime commentModDate;

    @Column(name = "comment_visible", nullable = false)
    private Boolean commentVisible;

    //relation with doc_id
    @ManyToOne
    @JoinColumn(name = "doc_id", nullable = false)
    private Document document;

    //relation with doc_creator
    @ManyToOne
    @JoinColumn(name = "comment_creator")
    private Member commentCreator;

    //relation with doc_modifier
    @ManyToOne
    @JoinColumn(name = "comment_modifier")
    private Member commentModifier;




    @PrePersist
    public void prePersist() {
        commentVisible = commentVisible == null ? true : commentVisible;
    }
}
