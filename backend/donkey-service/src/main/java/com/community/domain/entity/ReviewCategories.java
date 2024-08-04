package com.community.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Review_Categories")
public class ReviewCategories {

    @Id
    @Column(name = "review_Categories_id")
    private String reviewCategoriesId;

    @Column(name = "category_name")
    private String categoryName;
}
