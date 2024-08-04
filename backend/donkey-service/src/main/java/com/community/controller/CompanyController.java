package com.community.controller;

import com.community.domain.dto.CompanyDataResponse;
import com.community.service.CompaniesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    private CompaniesService companiesService;

    public CompanyController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    @GetMapping("/companydata/{com_id}")
    public ResponseEntity<CompanyDataResponse> bringCompanyDataById(@PathVariable("com_id")String comId) {
        CompanyDataResponse response = companiesService.bringCompanyDataById(comId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/companydata/name/{com_Name}")
    public ResponseEntity<CompanyDataResponse> bringCompanyDataByName(@PathVariable("com_Name") String comName) {
        CompanyDataResponse response = companiesService.bringCompanyDataByName(comName);
        return ResponseEntity.ok().body(response);
    }

}
