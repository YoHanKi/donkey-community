package com.community.repository;

import com.community.domain.entity.PasswordQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordQuestionRepository extends JpaRepository<PasswordQuestion, String> {
    Optional<PasswordQuestion> findByQuestion(String question);

    boolean existsByQuestion(String question);
}
