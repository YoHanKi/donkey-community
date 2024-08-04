package com.community.service;

import com.community.domain.dto.BoardResponse;
import com.community.domain.dto.CreateBoardRequest;
import com.community.domain.dto.UpdateBoardApproveRequest;
import com.community.domain.entity.Board;
import com.community.domain.entity.Companies;
import com.community.domain.entity.Industry;
import com.community.domain.entity.Member;
import com.community.repository.BoardRepository;
import com.community.repository.CompaniesRepository;
import com.community.repository.IndustryRepository;
import com.community.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;
    private IndustryRepository industryRepository;
    private CompaniesRepository companiesRepository;
    private MemberRepository memberRepository;
    private EntityManager entityManager;

    @Transactional
    public Board saveBoard(CreateBoardRequest body) {
        //UUID 생성
        String createUUID = UUID.randomUUID().toString();

        //업종 객체 확인
        Optional<Industry> industry = industryRepository.findById(body.getIndustryId());

        //기업 DB에 있는지 확인(현재는 가데이터 혹은 수작업으로 처리 이후 API를 통해 자동으로 DB에 주입)
        Optional<Companies> companies = companiesRepository.findById(body.getComId());

        //멤버가 실제 조회 되는지
        Optional<Member> member = memberRepository.findByEmail(body.getRequesterEmail());

        //회사명은 빈값이면 안됨
        if(body.getBoardName().equals("")){
            throw new IllegalArgumentException("게시판 명은 반드시 입력해야합니다.");
        }

        //회사명은 겹치면 안됨
        if(boardRepository.existsByBoardName(body.getBoardName())){
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
        if(member.isEmpty()){
            throw new IllegalArgumentException("유효하지 않은 회원입니다.");
        }

        Board board = boardRepository.save(Board.builder()
                        .boardId(createUUID)
                        .boardName(body.getBoardName())
                        .industry(industry.get())
                        .companies(companies.orElse(null))
                        .memberUser(member.get())
                        //승인 대기
                        .approve(false)
                        .build()
        );

        return board;
    }

    /**
     * 게시판 승인 여부 변경
     * 만약 사용자가 Admin이 아닐 경우 에러 발생
     * @param request
     * @return
     */
    public Board updateApprove(UpdateBoardApproveRequest request) {
        Board board = boardRepository.findById(request.getBoardId()).orElse(null);

        if(board == null){
            throw new IllegalArgumentException("유효하지 않은 게시판 Id입니다.");
        }

        board.setApprove(request.getApproval());

        return boardRepository.save(board);
    }

    public List<BoardResponse> showAllBoardByCondition(Boolean approveCondition) {
        List<Board> boardList = entityManager.createQuery("select b from Board b where approve=" + approveCondition.toString(), Board.class).getResultList();

        return boardList.stream().map(board -> {
            return new BoardResponse(board.getBoardId(), board.getBoardName(),
                board.getCompanies() == null ? "" : board.getCompanies().getComName(),
                board.getIndustry() == null ? "" : board.getIndustry().getIndustryName()
            );}).toList();
    }
}


