package com.community.domain.entity;

import com.community.repository.MemberRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = AuditingEntityListener.class)
public class Member implements UserDetails  {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "email",nullable = false, unique = true)
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

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    private LocalDateTime modDate;

    //relation with question
    @ManyToOne
    @JoinColumn(name = "password_question_id", nullable = false)
    private PasswordQuestion passwordQuestion;

    //relation with MemberRole
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id", nullable = false)
    private MemberRole memberRole;

    //탈퇴 여부 withdrawal 디폴트 값 설정
    @PrePersist
    public void prePersist() {
        withdrawal = withdrawal == null ? false : withdrawal;
    }

    /**
     * 시큐리티 관련 설정
     */



    @Override
    @JsonIgnore
    public Set<GrantedAuthority> getAuthorities() {
        return Set.of(memberRole);
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환 (true: 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환 (true: 잠금 안됨)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드의 만료 여부 반환 (true: 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 여부 반환 (true: 사용 가능)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
