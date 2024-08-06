package com.community.member.member.repository;

import com.community.member.member.domain.entity.Member;
import com.community.member.member.domain.entity.MemberInterests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberInterestsRepository extends JpaRepository<MemberInterests, Long> {
    List<MemberInterests> findAllByMember(Member member);
    List<MemberInterests> findAllByMember_Email(String email);
}
