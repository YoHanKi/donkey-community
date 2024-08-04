package com.community.util;

import com.community.domain.dto.CompanyDataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Component
public class CompanyDataAPI {
    @Value("${company.API}")
    private String key;

    /**
     * corpNm : 사업자명
     * corpEnsnNm : 사업자 영문명
     * enpBsadr : 사업자 주소
     * enpHmpgUrl : 사업자 홈페이지
     * enpTlno : 전화 번호
     */
    public CompanyDataResponse bringCompanyData(String reg, String comName) {
        CompanyDataResponse APIData = new CompanyDataResponse();
        RestTemplate restTemplate = new RestTemplate();

        String URL = "https://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2?ServiceKey=" + key + "&pageNo=1&numOfRows=1&resultType=json&crno=" + reg + "&corpNm=" + comName;
        ResponseEntity<LinkedHashMap> response = restTemplate.getForEntity(URL, LinkedHashMap.class);


        if (response.getStatusCode().is2xxSuccessful()) {
            LinkedHashMap<String, Object> responseResult = response.getBody();
            LinkedHashMap<String, Object> responseBody = (LinkedHashMap<String, Object>) responseResult.get("response");
            LinkedHashMap<String, Object> body = (LinkedHashMap<String, Object>) responseBody.get("body");
            LinkedHashMap<String, Object> items = (LinkedHashMap<String, Object>) body.get("items");
            ArrayList<LinkedHashMap> item = (ArrayList<LinkedHashMap>) items.get("item");
            LinkedHashMap<String, Object> companyData = item.get(0);

            APIData.setCorpNm((String) companyData.get("corpNm"));
            APIData.setCorpEnsnNm((String) companyData.get("corpEnsnNm"));
            APIData.setEnpBsadr((String) companyData.get("enpBsadr"));
            APIData.setEnpHmpgUrl((String) companyData.get("enpHmpgUrl"));
            APIData.setEnpTlno((String) companyData.get("enpTlno"));
        } else throw new IllegalArgumentException("잘못된 입력입니다.");

        return APIData;
    }
}
