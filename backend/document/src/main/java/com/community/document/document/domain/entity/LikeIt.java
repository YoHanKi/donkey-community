package com.community.document.document.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "like_it")
public class LikeIt {

    @Id
    @Column(name = "like_id")
    private String likeId;

    @Column(name = "like_count")
    private Long likeCount;

    @PrePersist
    public void prePersist() {
        likeCount = likeCount == null ? 0 : likeCount;
    }
}
