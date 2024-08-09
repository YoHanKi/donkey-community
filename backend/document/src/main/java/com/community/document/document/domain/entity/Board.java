package com.community.document.document.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Board {

    @Id
    @Column(name = "board_id")
    private String boardId;

    @Column(name = "board_name", nullable = false)
    private String boardName;

    @CreatedDate
    @Column(name = "board_create_date")
    private LocalDateTime boardCreateDate;

    //relation with industry
    @ManyToOne
    @JoinColumn(name = "industry_id")
    private Industry industry;

    //relation with companies
    @ManyToOne
    @JoinColumn(name = "com_id")
    private Companies companies;

    //relation with admin
    @Column(name = "board_creator_email")
    private String memberAdminEmail;

    //relation with user
    @Column(name = "board_requester_email", nullable = false)
    private String memberUserEmail;

    @Column(name = "commu_board_request_approve")
    private Boolean approve;
}