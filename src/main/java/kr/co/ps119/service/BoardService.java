package kr.co.ps119.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberRepository;

@Service
public class BoardService {

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
		board.setCreateDate(new Date(System.currentTimeMillis()));
		board.setContent("Welcome to IMCOLLA!");
		
		Board createdBoard = boardRepo.save(board);
		
		return createdBoard.getId();
	}
	
}
