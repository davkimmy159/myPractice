package kr.co.ps119.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.vo.MemberForm;

@Service
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private int loginTryCnt = 0;
	
	public boolean getLoginMember(String targetEmailId) {
		boolean exist = false;
		Member loginMember = memberRepo.findByEmail(targetEmailId);
		
		if(loginMember == null) {
			loginTryCnt++;
		} else {
			
			exist = true;
		}

		return exist;
	}
	
	public boolean createAccount(MemberForm memberForm) {
		MemberForm targetMemberForm = memberForm;
		Member saveEntity = new Member();
		boolean successFlag = false;
		
		String rawPassword = memberForm.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		memberForm.setPassword(encodedPassword);
		
		BeanUtils.copyProperties(targetMemberForm, saveEntity);
		
		Member resultEntity = memberRepo.save(saveEntity);
		
		// Check whether unexpected save error is occurred
		if(resultEntity != null) {
			successFlag = true;
		}
		
		return successFlag;
	}
	
	public List<Member> test1() {
		Member member = new Member("email.com_temp", "username_temp", "password_temp");
		
		memberRepo.save(member);
		
		List<Member> list = memberRepo.findAll();
		
		return list;
	}
}
