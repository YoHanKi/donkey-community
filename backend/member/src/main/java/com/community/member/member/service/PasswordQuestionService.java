package com.community.member.member.service;

import com.community.member.member.domain.dto.MemberDTO;
import com.community.member.member.domain.entity.PasswordQuestion;
import com.community.member.member.repository.PasswordQuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PasswordQuestionService {
    private final PasswordQuestionRepository passwordQuestionRepository;

    @Transactional
    public PasswordQuestion savePasswordQuestion(MemberDTO.AddPasswordQuestionRequest request){

        if(passwordQuestionRepository.existsByQuestion(request.getQuestion())){
            throw new IllegalArgumentException("동일한 질문이 이미 존재합니다.");
        }

        if(request.getQuestion() != null && request.getQuestion().length() > 200){
            throw new IllegalArgumentException("질문의 길이가 너무 깁니다.");
        }

        return passwordQuestionRepository.save(PasswordQuestion.builder()
                .question(request.getQuestion())
                .build());
    }

	public List<MemberDTO.PasswordQuestionResponse> showAllPasswordQuestion() {
        List<PasswordQuestion> passwordQuestionList = passwordQuestionRepository.findAll();

        return passwordQuestionList.stream().map(question -> new MemberDTO.PasswordQuestionResponse(question.getPasswordQuestionId(), question.getQuestion())
			).toList();
	}
}