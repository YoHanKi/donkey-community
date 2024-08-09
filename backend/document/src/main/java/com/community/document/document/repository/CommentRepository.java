package com.community.document.document.repository;

import com.community.document.document.domain.entity.Comment;
import com.community.document.document.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    Optional<List<Comment>> findAllByDocument(Document document);
    Optional<List<Comment>> findAllByCommentCreatorEmail(String memberEmail);
    void deleteAllByDocument(Document document);

}
