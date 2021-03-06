package kr.co.ps119.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.BoardHistory;
import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardHistoryRepository;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.repository.MemoRepository;

@Service
@Transactional
public class BoardService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;

	@Autowired
	private BoardHistoryRepository boardHisRepo;

	@Autowired
	private MemoRepository memoRepo;
	
	public Board findOne(Long boardId) {
		return boardRepo.findOne(boardId);
	}
	
	public List<Board> findByIdInAndMemberId(List<Long> id, Long memberId) {
		return boardRepo.findByIdInAndMemberId(id, memberId);
	}
	
	public List<Board> findAllBoardsWithPageRequest(Pageable pageable) {
		Page<Board> boardListPage = boardRepo.findAll(pageable);
		
		return boardListPage.getContent();
	}
	
	public List<Board> findAllBoardsWithPageRequest(Long memberId, Pageable pageable) {
		List<Board> boardList = boardRepo.findByMemberId(memberId, pageable);
		
		return boardList;
	}
	
	public Long createBoard(String boardName, String ownerUsername) {
		Board board = new Board();
		Member boardOwner = memberRepo.findByUsername(ownerUsername);
		
		board.setTitle(boardName);
		board.setMember(boardOwner);
//		board.setCreateDate(new Date(System.currentTimeMillis()));
//		board.setContent("Welcome to IMCOLLA!");
		
		Board createdBoard = boardRepo.save(board);
		Long createdBoardId;
		
		if(createdBoard != null) {
			createdBoardId = createdBoard.getId();
		} else {
			createdBoardId = -1L;
		}
		
		return createdBoardId;
	}
	
	public Long updateBoardDBContent(String username, Long boardId, String editorContent) {
		Member member = memberRepo.findByUsername(username);
		Board boardToBeUpdated = boardRepo.findOne(boardId);

		Long updateCount = boardToBeUpdated.getUpdateCount() + 1L;
		
		boardToBeUpdated.setContent(editorContent);
		boardToBeUpdated.setUpdateCount(updateCount);
		boardToBeUpdated.setLastUpdateDate(LocalDateTime.now());
		
		Board boardAfterUpdate = boardRepo.save(boardToBeUpdated);
		Long boardIdAfterUpdate;
		
		if(boardAfterUpdate != null) {
			boardIdAfterUpdate = boardAfterUpdate.getId();
			createOneBoardHistory(member, boardAfterUpdate, "Board DB content update");
		} else {
			boardIdAfterUpdate = -1L;
		}
		
		return boardIdAfterUpdate;
	}
	
	private void createOneBoardHistory(Member member, Board board, String historyContent) {
		BoardHistory boardHistory = new BoardHistory();
		boardHistory.setMember(member);
		boardHistory.setBoard(board);
		boardHistory.setContent(historyContent);
		boardHisRepo.save(boardHistory);
	}
	
	public Board findOneWithAddHitCount(Long boardId, Principal principal) {
//		String jpql = "UPDATE Board AS board SET board.hitCount = board.hitCount + 1 WHERE board.id = :boardId";
		Board board = boardRepo.findOne(boardId);

		if(board != null) {
			if(!(principal.getName().equals(board.getMember().getUsername()))) {
				board.setHitCount(board.getHitCount() + 1L);
				
				/*
				em.createQuery(jpql)
				  .setParameter("boardId", boardId)
				  .executeUpdate();
				*/
			}
		} else {
			board = Board.getEmptyBoard();
		}
		
		return board;
	}
	
	public Long getTotalCountOfBoards() {
		Long total = boardRepo.count();
		
		return total;
	}
	
	public Long getTotalCountOfBoards(Long memberId) {
		Long total = boardRepo.countByMemberId(memberId);

		return total;
	}
	
	public void deleteById(Long boardId) {
		boardRepo.delete(boardId);
	}
	
	public List<Board> deleteByIdInAndMemberId(List<Long> id, Long memberId) {
		return boardRepo.deleteByIdInAndMemberId(id, memberId);
	}
	
	public Long getTotalCountOfBoardsTitleLike(String title) {
		Long total = boardRepo.countByTitleLike(title);
		
		return total;
	}
	
	public Long getTotalCountOfBoardsTitleLike(Long memberId, String title) {
		Long total = boardRepo.countByMemberIdAndTitleLike(memberId, title);

		return total;
	}
	
	public List<Board> findAllBoardsTitleLikeWithPageRequest(String title, Pageable pageable) {
		List<Board> boardListPage = boardRepo.findByTitleLike(title, pageable);
		
		return boardListPage;
	}
	
	public List<Board> findAllBoardsTitleLikeWithPageRequest(Long memberId, String title, Pageable pageable) {
		List<Board> boardList = boardRepo.findByMemberIdAndTitleLike(memberId, title, pageable);
		
		return boardList;
	}
}
