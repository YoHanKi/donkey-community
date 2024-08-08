package com.community.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CompanyDataResponse {
    private String corpNm;
    private String corpEnsnNm;
    private String enpBsadr;
    private String enpHmpgUrl;
    private String enpTlno;

    public CompanyDataResponse() {
        corpNm = "";
        corpEnsnNm = "";
        enpBsadr = "";
        enpHmpgUrl = "";
        enpTlno = "";
    }
}
