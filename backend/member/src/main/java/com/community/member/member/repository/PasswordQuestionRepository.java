package com.community.member.member.repository;


import com.community.member.member.domain.entity.PasswordQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordQuestionRepository extends JpaRepository<PasswordQuestion, Long> {
    Optional<PasswordQuestion> findByQuestion(String question);
    boolean existsByQuestion(String question);
}
