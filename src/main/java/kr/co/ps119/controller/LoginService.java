package kr.co.ps119.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberRepository;

@Service
public class LoginService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private int loginTryCnt = 0;
	
	public boolean getLoginMemberInfo(String targetEmailId) {
		boolean exist = false;
		Member loginMember = memberRepo.findByEmail(targetEmailId);
		
		if(loginMember == null) {
			loginTryCnt++;
		} else {
			
			exist = true;
		}

		return exist;
	}
	
	
}
