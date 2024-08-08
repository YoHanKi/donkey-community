package com.community.member.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String roleName;

    //권한 처리
    @Override
    public String getAuthority() {
        return roleName;
    }
}
