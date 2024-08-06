package com.community.member.member.domain.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "password_question")
public class PasswordQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_question_id")
    private Long passwordQuestionId;

    @Column(name = "question", nullable = false, unique = true)
    private String question;

}
