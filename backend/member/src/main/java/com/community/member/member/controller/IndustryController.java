package com.community.member.member.controller;

import com.community.member.global.dto.ErrorResult;
import com.community.member.member.domain.dto.IndustryDTO;
import com.community.member.member.domain.entity.Industry;
import com.community.member.member.service.IndustryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/member")
public class IndustryController {
    private IndustryService industryService;
    @GetMapping("/industry")
    public ResponseEntity<List<IndustryDTO.IndustryResponse>> searchIndustry(){
        List<Industry> industryList = industryService.showAllIndustry();
        List<IndustryDTO.IndustryResponse> response = industryList.stream().map(industry -> IndustryDTO.IndustryResponse.builder()
                    .industryId(industry.getIndustryId())
                    .industryName(industry.getIndustryName())
                    .industryDescription(industry.getIndustryDescription() == null ? "" :
                        industry.getIndustryDescription())
                    .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/admin/industry")
    public ResponseEntity<ErrorResult> saveInudstry(@RequestBody IndustryDTO.AddIndustryRequest request){
        industryService.saveIndustry(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ErrorResult("성공", "정상적으로 추가되었습니다."));
    }
}