package com.community.document.document.repository;


import com.community.document.document.domain.entity.Board;
import com.community.document.document.domain.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, String> {
	boolean existsByBoardName(String boardName);
	boolean existsByCompanies(Companies companies);
}
