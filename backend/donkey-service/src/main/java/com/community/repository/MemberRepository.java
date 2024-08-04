package com.community.repository;

import com.community.domain.entity.Member;
import com.community.domain.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);

    Optional<List<Member>> findAllByMemberRole(MemberRole memberRole);

    boolean existsByEmail(String email);

    boolean existsByNickname(String Nickname);

    @Modifying
    @Query("UPDATE Member SET withdrawal = :withdrawal WHERE email = :email")
    void updateWithdrawal(boolean withdrawal, String email);


}
