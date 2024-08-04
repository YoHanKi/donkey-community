package com.community.repository;

import com.community.domain.entity.Industry;
import com.community.domain.entity.Member;
import com.community.domain.entity.MemberInterests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberInterestsRepository extends JpaRepository<MemberInterests, String> {
    Optional<List<MemberInterests>> findAllByMember(Member member);
}
