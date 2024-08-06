package com.community.service;

import com.community.domain.dto.AddPasswordQuestionRequest;
import com.community.domain.dto.PasswordQuestionResponse;
import com.community.domain.entity.PasswordQuestion;
import com.community.repository.PasswordQuestionRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordQuestionService {
    private final PasswordQuestionRepository passwordQuestionRepository;

    @Transactional
    public PasswordQuestion savePasswordQuestion(AddPasswordQuestionRequest request){
        String createUUID = UUID.randomUUID().toString();

        if(passwordQuestionRepository.existsByQuestion(request.getQuestion())){
            throw new IllegalArgumentException("동일한 질문이 이미 존재합니다.");
        }

        if(request.getQuestion() != null && request.getQuestion().length() > 200){
            throw new IllegalArgumentException("질문의 길이가 너무 깁니다.");
        }

        PasswordQuestion question = passwordQuestionRepository.save(PasswordQuestion.builder()
                .passwordQuestionId(createUUID)
                .question(request.getQuestion())
                .build());

        return question;
    }

	public List<PasswordQuestionResponse> showAllPasswordQuestion() {
        List<PasswordQuestion> passwordQuestionList = passwordQuestionRepository.findAll();

        return passwordQuestionList.stream().map(question -> new PasswordQuestionResponse(question.getPasswordQuestionId(), question.getQuestion())
			).toList();
	}
}
