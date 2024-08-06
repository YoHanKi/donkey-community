package com.community.member.member.repository;


import com.community.member.member.domain.entity.Member;
import com.community.member.member.domain.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);
    List<Member> findAllByMemberRole_RoleName(String roleName);
    boolean existsByEmailAndPasswordQuestion(String email, QuestionType questionType);
    boolean existsByEmail(String email);
    boolean existsByNickname(String Nickname);
    @Modifying
    @Query("UPDATE Member SET withdrawal = :withdrawal WHERE email = :email")
    void updateWithdrawal(boolean withdrawal, String email);
}
