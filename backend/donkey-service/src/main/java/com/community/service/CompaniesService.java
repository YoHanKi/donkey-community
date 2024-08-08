package com.community.service;

import com.community.domain.dto.CompanyDataResponse;
import com.community.domain.entity.Companies;
import com.community.repository.CompaniesRepository;
import com.community.util.CompanyDataAPI;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompaniesService {
    private final CompaniesRepository companiesRepository;
    private final CompanyDataAPI companyDataAPI;

    //API 확인 해야함
    public CompanyDataResponse bringCompanyDataById(String comId) {
        Companies companies = companiesRepository.findById(comId).orElseThrow(()->new EntityNotFoundException("회사를 찾을 수 없습니다."));
        return companyDataAPI.bringCompanyData(companies.getRegNum(), companies.getComName());
    }

    public CompanyDataResponse bringCompanyDataByName(String comName) {
        Companies companies = companiesRepository.findByComName(comName).orElseThrow(()->new EntityNotFoundException("회사를 찾을 수 없습니다."));
        return companyDataAPI.bringCompanyData(companies.getRegNum(), companies.getComName());
    }
}
