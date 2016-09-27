package kr.co.ps119.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.vo.MemberForm;

@Service
public class MemberService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	BoardRepository boardRepo;
	
	@Autowired
	CommentRepository commentRepo;
	
	public List<Member> test1() {
		Member member = new Member("email.com_temp", "username_temp", "password_temp");
		
		memberRepo.save(member);
		
		List<Member> list = memberRepo.findAll();
		
		return list;
	}
	
	public boolean createAccount(MemberForm memberForm) {
		MemberForm tmpMemberForm = memberForm;
		Member member = new Member();
		
		BeanUtils.copyProperties(tmpMemberForm, member);
		
		memberRepo.save(member);
		return true;
	}
}
