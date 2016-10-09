package kr.co.ps119.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Member;
import kr.co.ps119.entity.MemberAuthority;
import kr.co.ps119.flag.NewAccount;
import kr.co.ps119.repository.AuthorityRepository;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberAuthorityRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.vo.MemberForm;

@Service
public class CreateAccountService {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private MemberAuthorityRepository memAuthRepo;
	
	@Autowired
	private AuthorityRepository authRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public NewAccount createAccount(MemberForm memberForm) {
		MemberForm targetMemberForm = memberForm;

		Member checkMember;
		// Checks email duplication
		checkMember = memberRepo.findByEmail(targetMemberForm.getEmail());
		if (checkMember != null) {
			return NewAccount.DUPLICATE_EMAIL;
		}

		// Checks username duplication
		checkMember = memberRepo.findByUsername(targetMemberForm.getUsername());
		if(checkMember != null) {
			return NewAccount.DUPLICATE_USERNAME;
		}
		
		Member saveEntity = new Member();
		
		String rawPassword = targetMemberForm.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		targetMemberForm.setPassword(encodedPassword);
		
		BeanUtils.copyProperties(targetMemberForm, saveEntity);
		
		MemberAuthority memAuth = new MemberAuthority();
		memAuth.setAuthority(authRepo.findOne(1));
		memAuth.setMember(saveEntity);
		
		Member resultEntity = memberRepo.save(saveEntity);
		
		// Checks whether unexpected save error has been occurred
		if(resultEntity == null) {
			return NewAccount.UNEXPECTED_SERVER_ERROR;
		}
		
		return NewAccount.SUCCESSFUL;
	}
}
