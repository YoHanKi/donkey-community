package com.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FindPasswordResponse {
    private String email;

    public FindPasswordResponse(FindPasswordRequest request) {
        email = request.getEmail();
    }
}
