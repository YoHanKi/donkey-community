package com.community.service;

import com.community.domain.dto.AddIndustryRequest;
import com.community.domain.entity.Industry;
import com.community.repository.IndustryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IndustryService {
    private final IndustryRepository industryRepository;

    public List<Industry> showAllIndustry(){
        return industryRepository.findAll();
    }

    public Industry saveIndustry(AddIndustryRequest request) {
        final int MAX_NAME_LENGTH = 20;
        final int MAX_NAME_COMMENT = 255;

        if(industryRepository.existsByIndustryName(request.getIndustryName())){
            throw new IllegalArgumentException("이미 존재하는 업종입니다.");
        }

        if(request.getIndustryName().isEmpty() || request.getIndustryName() == null){
            throw new IllegalArgumentException("업종 이름은 필수입니다.");
        }

        if(request.getIndustryName().length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException("업종 이름은 20자 이상 입력될 수 없습니다.");
        }

        if(!request.getIndustryComment().isEmpty() && request.getIndustryComment().length() > MAX_NAME_COMMENT){
            throw new IllegalArgumentException("업종 코멘트는 255자를 초과할 수 없습니다.");
        }

        return industryRepository.save(Industry.builder()
                .industryId(UUID.randomUUID().toString())
                .industryName(request.getIndustryName())
                .industryDescription(request.getIndustryComment())
                .build());
    }
}
