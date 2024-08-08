package com.community.document.document.service;

import com.community.document.document.domain.dto.BoardDTO;
import com.community.document.document.domain.entity.Board;
import com.community.document.document.domain.entity.Companies;
import com.community.document.document.domain.entity.Industry;
import com.community.document.document.repository.BoardRepository;
import com.community.document.document.repository.CompaniesRepository;
import com.community.document.document.repository.IndustryRepository;
import com.community.document.global.dto.RedisObject;
import com.community.document.global.util.RedisUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final IndustryRepository industryRepository;
    private final CompaniesRepository companiesRepository;
    private final RedisUtil redisUtil;
    private final EntityManager entityManager;

    @Transactional
    public Board saveBoard(BoardDTO.CreateBoardRequest request) {
        //UUID 생성
        String createUUID = UUID.randomUUID().toString();

        //업종 객체 확인
        Optional<Industry> industry = industryRepository.findById(request.getIndustryId());

        //기업 DB에 있는지 확인(현재는 가데이터 혹은 수작업으로 처리 이후 API를 통해 자동으로 DB에 주입)
        Optional<Companies> companies = companiesRepository.findById(request.getComId());

        //멤버가 실제 조회 되는지
        RedisObject memberData = redisUtil.getDocumentData(request.getRequesterEmail());

        //회사명은 빈값이면 안됨
        if(request.getBoardName().isEmpty()){
            throw new IllegalArgumentException("게시판 명은 반드시 입력해야합니다.");
        }

        //회사명은 겹치면 안됨
        if(boardRepository.existsByBoardName(request.getBoardName())){
            throw new IllegalArgumentException("동일한 이름의 게시판이 존재합니다.");
        }

        //회사 데이터는 실제로 없을 수 있음
        if(companies.isPresent() && boardRepository.existsByCompanies(companies.get())){
            throw new IllegalArgumentException("동일한 기업을 참조하는 게시판이 존재합니다.");
        }

        //업종 코드가 존재하는지 검사
        if(industry.isEmpty()){
            throw new IllegalArgumentException("유효하지 않은 업종 ID입니다.");
        }

        //멤버 코드가 존재하는지 검사
        if(memberData==null){
            throw new IllegalArgumentException("유효하지 않은 회원입니다.");
        }

        Board board = boardRepository.save(Board.builder()
                        .boardId(createUUID)
                        .boardName(request.getBoardName())
                        .industry(industry.get())
                        .companies(companies.orElse(null))
                        .memberUserEmail(memberData.getNickname())
                        //승인 대기
                        .approve(false)
                        .build()
        );

        return board;
    }

    public Board updateApprove(BoardDTO.UpdateBoardApproveRequest request) {
        Board board = boardRepository.findById(request.getBoardId()).orElse(null);

        if(board == null){
            throw new IllegalArgumentException("유효하지 않은 게시판 Id입니다.");
        }

        board.setApprove(request.getApproval());

        return boardRepository.save(board);
    }

    public List<BoardDTO.BoardResponse> showAllBoardByCondition(Boolean approveCondition) {
        List<Board> boardList = entityManager.createQuery("select b from Board b where approve=" + approveCondition.toString(), Board.class).getResultList();

        return boardList.stream().map(board -> {
            return new BoardDTO.BoardResponse(board.getBoardId(), board.getBoardName(),
                board.getCompanies() == null ? "" : board.getCompanies().getComName(),
                board.getIndustry() == null ? "" : board.getIndustry().getIndustryName()
            );}).toList();
    }
}