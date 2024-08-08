package com.community.document.document.domain.entity;

import jakarta.persistence.*;
import lombok.*;
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
public class Document {

    @Id
    @Column(name = "doc_id")
    private String docId;

    @Column(name = "doc_title", nullable = false)
    private String docTitle;

    @Column(name = "doc_content", nullable = false)
    private String docContent;

    @CreatedDate
    @Column(name = "doc_create_date")
    private LocalDateTime docCreateDate;

    @LastModifiedDate
    @Column(name = "doc_mod_date")
    private LocalDateTime docModDate;

    @Column(name = "doc_visible")
    private Boolean docVisible;

    //relation with doc_creator(Member)
    @Column(name = "doc_creator_email", nullable = false)
    private String docCreatorEmail;

    //relation with doc_modifier(Member)
    @Column(name = "doc_modifier_email")
    private String docModifierEmail;

    //relation with board
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    //relation with industry
    @ManyToOne
    @JoinColumn(name = "industry_id", nullable = false)
    private Industry industry;

    @PrePersist
    public void prePersist() {
        docVisible = docVisible == null ? true : docVisible;
    }
}