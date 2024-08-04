package com.community.controller;

import com.community.domain.dto.IndustryResponse;
import com.community.domain.entity.Industry;
import com.community.service.IndustryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
public class IndustryController {
    private IndustryService industryService;
    @GetMapping("/industry")
    public ResponseEntity<List<IndustryResponse>> searchIndustry(){
        List<Industry> industryList = industryService.showAllIndustry();
        List<IndustryResponse> response = industryList.stream().map(industry -> IndustryResponse.builder()
                    .industryId(industry.getIndustryId())
                    .industryName(industry.getIndustryName())
                    .industryDescription(industry.getIndustryDescription() == null ? "" :
                        industry.getIndustryDescription())
                    .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
