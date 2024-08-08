package com.community.document.document.repository;

import com.community.document.document.domain.entity.Board;
import com.community.document.document.domain.entity.Document;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
    Optional<List<Document>> findAllByDocCreatorEmail(String memberEmail);
    Slice<Document> findAllByBoard(Board board, Pageable page);
    Slice<Document> findByDocContentContaining(String keyword, Pageable page);
}
