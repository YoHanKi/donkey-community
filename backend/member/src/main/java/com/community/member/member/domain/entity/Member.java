package com.community.member.member.domain.entity;

import com.community.member.member.domain.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@Table(indexes = {
        @Index(name = "idx_email", columnList = "email")})
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // 정규식 : /^(?=.\d)(?=.[A-Z])(?=.[a-z])(?=.[^\w\d\s:])([^\s]){8,16}$/gm
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "find_password_answer", nullable = false)
    private String findPasswordAnswer;

    @Column(name = "withdrawal", nullable = false)
    private Boolean withdrawal;

    //relation with question
    @ManyToOne
    @JoinColumn(name = "password_question_id", nullable = false)
    private PasswordQuestion passwordQuestion;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private LocalDateTime modDate;

    //relation with MemberRole
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id", nullable = false)
    private MemberRole memberRole;

    //탈퇴 여부 withdrawal 디폴트 값 설정
    @PrePersist
    public void prePersist() {
        withdrawal = withdrawal == null ? false : withdrawal;
    }
}
