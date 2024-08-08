package com.community.service;

import com.community.domain.dto.CompanyDataResponse;
import com.community.domain.entity.Companies;
import com.community.repository.CompaniesRepository;
import com.community.util.CompanyDataAPI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CompaniesService {

    private CompaniesRepository companiesRepository;
    private CompanyDataAPI companyDataAPI;

    public CompaniesService(CompaniesRepository companiesRepository, CompanyDataAPI companyDataAPI) {
        this.companiesRepository = companiesRepository;
        this.companyDataAPI = companyDataAPI;
    }

    //API 확인 해야함
    public CompanyDataResponse bringCompanyDataById(String comId) {
        Companies companies = companiesRepository.findById(comId).orElseThrow(()->new EntityNotFoundException("회사를 찾을 수 없습니다."));
        CompanyDataResponse response = companyDataAPI.bringCompanyData(companies.getRegNum(), companies.getComName());
        return response;
    }

    public CompanyDataResponse bringCompanyDataByName(String comName) {
        Companies companies = companiesRepository.findByComName(comName).orElseThrow(()->new EntityNotFoundException("회사를 찾을 수 없습니다."));
        CompanyDataResponse response = companyDataAPI.bringCompanyData(companies.getRegNum(), companies.getComName());
        return response;
    }

}
