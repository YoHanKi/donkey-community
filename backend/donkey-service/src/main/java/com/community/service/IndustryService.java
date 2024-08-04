package com.community.service;

import com.community.domain.dto.AddIndustryRequest;
import com.community.domain.entity.Industry;
import com.community.repository.IndustryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class IndustryService {
    private IndustryRepository industryRepository;
    private final int MAX_NAME_LENGTH = 20;
    private final int MAX_NAME_COMMENT = 255;

    /**
     * @biref
     * @return 목록
     */
    public List<Industry> showAllIndustry(){
        return industryRepository.findAll();
    }

    public Industry saveIndustry(AddIndustryRequest request) {
        if(industryRepository.existsByIndustryName(request.getIndustryName())){
            throw new IllegalArgumentException("이미 존재하는 업종입니다.");
        }

        if(request.getIndustryName().equals("") || request.getIndustryName() == null){
            throw new IllegalArgumentException("업종 이름은 필수입니다.");
        }

        if(request.getIndustryName().length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException("업종 이름은 20자 이상 입력될 수 없습니다.");
        }

        if(!request.getIndustryComment().equals("") && request.getIndustryComment().length() > MAX_NAME_COMMENT){
            throw new IllegalArgumentException("업종 코멘트는 255자를 초과할 수 없습니다.");
        }

        String uuid = UUID.randomUUID().toString();


        Industry industry = industryRepository.save(Industry.builder()
                .industryId(uuid)
                .industryName(request.getIndustryName())
                .industryDescription(request.getIndustryComment())
                .build());

        return industry;
    }
}
