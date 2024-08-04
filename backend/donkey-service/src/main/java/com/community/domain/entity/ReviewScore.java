package com.community.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review_score")
public class ReviewScore {
    @Id
    @Column(name = "score_id")
    private String scoreId;

    @Column(name = "score")
    private Integer score;

    //relation with reviews
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Reviews reviews;

    //relation with review categories
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_Categories_id")
    private ReviewCategories categories;
}
