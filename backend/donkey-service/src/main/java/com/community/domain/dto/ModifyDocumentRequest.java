package com.community.domain.dto;

import com.community.domain.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDocumentRequest {
    private String docTitle;
    private String docContent;
}
