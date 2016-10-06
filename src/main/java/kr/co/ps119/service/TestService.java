package kr.co.ps119.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Authority;
import kr.co.ps119.entity.Member;
import kr.co.ps119.entity.MemberAuthority;
import kr.co.ps119.repository.AuthorityRepository;
import kr.co.ps119.repository.MemberAuthorityRepository;
import kr.co.ps119.repository.MemberRepository;

@Service
public class TestService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private MemberAuthorityRepository memAuthRepo;
	
	@Autowired
	private AuthorityRepository authRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void test1() {
		memberRepo.save(new Member("mail_3@mail.com", "encrypted_user_3", passwordEncoder.encode("password")));
		memberRepo.save(new Member("mail_4@mail.com", "encrypted_user_4", passwordEncoder.encode("password")));
	}
	
	public void test2() {
		Member member1 = memberRepo.findOne(1L);
		List<MemberAuthority> memAuthList = member1.getAuthorities();
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
