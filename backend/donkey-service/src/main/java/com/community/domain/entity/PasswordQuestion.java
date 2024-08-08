package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "password_question_id")
    private String passwordQuestionId;

    @Column(name = "question", nullable = false, unique = true)
    private String question;

}
