package com.community.repository;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, String> {
    Optional<MemberRole> findAllByMemberRoleName(String MemberRole);

}
