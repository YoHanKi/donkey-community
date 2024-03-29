package com.community.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_role")
public class MemberRole implements GrantedAuthority {

    @Id
    @Column(name = "authority_id")
    private String memberRoleId;

    @Column(name = "authority_name", nullable = false)
    private String memberRoleName;

//    //relation with MemberRole
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_have", nullable = false)
//    private Member memberHave;

    //권한 처리
    @Override
    public String getAuthority() {
        return memberRoleName;
    }
}
