package com.community.repository;

import com.community.domain.entity.Board;
import com.community.domain.entity.Document;
import com.community.domain.entity.Member;
import com.community.domain.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
    Optional<List<Document>> findAllByDocCreator(Member member);
    Slice<Document> findAllByBoard(Board board, Pageable page);
    Slice<Document> findByDocContentContaining(String keyword, Pageable page);
}
