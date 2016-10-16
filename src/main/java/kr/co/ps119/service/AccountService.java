package kr.co.ps119.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Member;
import kr.co.ps119.entity.MemberAuthority;
import kr.co.ps119.flag.NewAccountFlag;
import kr.co.ps119.repository.AuthorityRepository;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.CommentRepository;
import kr.co.ps119.repository.MemberAuthorityRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.vo.MemberForm;

@Service
@Transactional
public class AccountService {
	
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
	
	public NewAccountFlag createAccount(MemberForm memberForm) {
		MemberForm targetMemberForm = memberForm;

		Member checkMember;
		
		// Checks email duplication
		checkMember = memberRepo.findByEmail(targetMemberForm.getEmail());
		if (checkMember != null) {
			return NewAccountFlag.DUPLICATE_EMAIL;
		}

		// Checks username duplication
		checkMember = memberRepo.findByUsername(targetMemberForm.getUsername());
		if(checkMember != null) {
			return NewAccountFlag.DUPLICATE_USERNAME;
		}
		
		
		/* Starts creating an account */
		
		Member saveEntity = new Member();
		
		// Encodes password 
		String rawPassword = targetMemberForm.getPassword();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		targetMemberForm.setPassword(encodedPassword);
		BeanUtils.copyProperties(targetMemberForm, saveEntity);

		// Sets basic authority of account
		MemberAuthority memAuth = new MemberAuthority();
		memAuth.setAuthority(authRepo.findOne(1));
		
		// Saves entity
		memAuth.setMember(saveEntity);
		
		// Gets entity that is just saved above
		Member resultEntity = memberRepo.save(saveEntity);
		
		// Checks whether unexpected save error has been occurred
		// ex) save error ...
		if(resultEntity == null) {
			return NewAccountFlag.UNEXPECTED_SERVER_ERROR;
		}
		
		return NewAccountFlag.SUCCESSFUL;
	}
}
