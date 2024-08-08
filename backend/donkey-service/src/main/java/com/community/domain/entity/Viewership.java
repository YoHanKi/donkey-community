package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Viewership {

    @Id
    @Column(name = "view_id")
    private String viewId;

    @Column(name = "view_count")
    private Long viewCount;

    @PrePersist
    public void prePersist() {
        viewCount = viewCount == null ? 0 : viewCount;
    }
}
