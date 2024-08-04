package com.community.repository;

import com.community.domain.entity.ReviewCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewCategoriesRepository extends JpaRepository<ReviewCategories, String> {
    Optional<ReviewCategories> findByCategoryName(String category);
}
