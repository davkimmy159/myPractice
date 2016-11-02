package kr.co.ps119.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberRepository;

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
	private CommentRepository commentRepo;
	
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
	
	public Long updateBoardDBContent(Long boardId, String editorContent) {
		Board boardToBeUpdated = boardRepo.findOne(boardId);

		Long updateCount = boardToBeUpdated.getUpdateCount() + 1L;
		
		boardToBeUpdated.setContent(editorContent);
		boardToBeUpdated.setUpdateCount(updateCount);
		boardToBeUpdated.setLastUpdateDate(LocalDateTime.now());
		
		Board boardAfterUpdate = boardRepo.save(boardToBeUpdated);
		Long boardIdAfterUpdate;
		
		if(boardAfterUpdate != null) {
			boardIdAfterUpdate = boardAfterUpdate.getId();
		} else {
			boardIdAfterUpdate = -1L;
		}
		
		return boardIdAfterUpdate;
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
	
	public List<Board> findAllBoardsOfMemberByUsername(String username) {
		String jpql = "SELECT board FROM Board AS board WHERE board.member = :member";
		Member member = memberRepo.findByUsername(username);
		
		List<Board> boardList = em.createQuery(jpql, Board.class)
								  .setParameter("member", member)
								  .getResultList();
		
		return boardList;
	}
	
	public List<Board> findAllBoards() {
		String jpql = "SELECT board FROM Board AS board";
		
		List<Board> boardList = em.createQuery(jpql, Board.class)
								  .getResultList();

		return boardList;
	}
	
	public void deleteOneBoardById(Long boardId) {
		boardRepo.delete(boardId);
	}
}
