package kr.co.ps119.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Authority;
import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Member;
import kr.co.ps119.entity.MemberAuthority;
import kr.co.ps119.repository.AuthorityRepository;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberAuthorityRepository;
import kr.co.ps119.repository.MemberRepository;

@Service
public class TestService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private MemberAuthorityRepository memAuthRepo;
	
	@Autowired
	private AuthorityRepository authRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private int titleCnt = 1;
	private Random random = new Random();
	
	public List<Board> getList() {
		List<Board> boardList = boardRepo.findAll();
		boardList.forEach(eachBoard -> eachBoard.getMember().getUsername());
		return boardList;
	}
	
	public int saveContent(String editorContent) {
		Board board = new Board();
		board.setTitle("Title " + titleCnt++);
		board.setContent(editorContent);
		board.setCreateDate(new Date(System.currentTimeMillis()));
		board.setMember(memberRepo.findOne((long)(random.nextInt(3) + 1)));
		boardRepo.save(board);
		
		return boardRepo.findAll().size();
	}
	
	public List<Board> deleteList() {
		boardRepo.deleteAll();
		return boardRepo.findAll();
	}
	
	public void test2() {
		Member member1 = memberRepo.findOne(1L);
		List<MemberAuthority> memAuthList = member1.getMemberAuthorities();
		List<Authority> authList = new ArrayList<>();
		
		for(MemberAuthority one : memAuthList) {
			System.out.println(one);
			authList.add(one.getAuthority());
		}
		
		for(Authority one : authList) {
			System.out.println(one);
		}
	}
}
