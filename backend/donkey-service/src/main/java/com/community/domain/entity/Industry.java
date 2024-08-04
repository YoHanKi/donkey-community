package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Industry {

    @Id
    @Column(name = "industry_id")
    private String industryId;

    @Column(name = "industry_name")
    private String industryName;

    @Column(name = "industry_description")
    private String industryDescription;
}
