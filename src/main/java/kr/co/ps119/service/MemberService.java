package kr.co.ps119.service;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberRepository;

@Service
@Transactional
public class MemberService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	/*
	@Value("#{etc['password.encodingString']}")
	@Value("${password}")
	public String passwordEncodingString;
	*/
	
	public Member findByUsername(String username) {
		return memberRepo.findByUsername(username);
	}
	
	public Member findOne(Long memberId) {
		return memberRepo.findOne(memberId);
	}
}
