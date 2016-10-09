package kr.co.ps119.event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.Authority;
import kr.co.ps119.entity.Member;
import kr.co.ps119.entity.MemberAuthority;
import kr.co.ps119.repository.AuthorityRepository;
import kr.co.ps119.repository.MemberAuthorityRepository;
import kr.co.ps119.repository.MemberRepository;

@Service
@Transactional
public class TestAccountCreationEventListener implements ApplicationListener<ApplicationReadyEvent> {


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
	
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		Member entity1= new Member("mail1@mail.com", "testUser1", passwordEncoder.encode("password"));
		
		MemberAuthority memAuth1A = new MemberAuthority();
		memAuth1A.setAuthority(authRepo.findOne(1));
		memAuth1A.setMember(entity1);
		
		MemberAuthority memAuth1B = new MemberAuthority();
		memAuth1B.setAuthority(authRepo.findOne(2));
		memAuth1B.setMember(entity1);
		
		memberRepo.save(entity1);

		
		Member entity2 = new Member("mail2@mail.com", "testUser2", passwordEncoder.encode("password"));
		
		MemberAuthority memAuth2 = new MemberAuthority();
		memAuth2.setAuthority(authRepo.findOne(1));
		memAuth2.setMember(entity2);
		
		memberRepo.save(entity2);
		
		
		Member entity3= new Member("mail3@mail.com", "testUser3", passwordEncoder.encode("password"));
		
		MemberAuthority memAuth3 = new MemberAuthority();
		memAuth3.setAuthority(authRepo.findOne(1));
		memAuth3.setMember(entity3);
		
		memberRepo.save(entity3);

		
		Member entity4= new Member("mail4@mail.com", "testUser4", passwordEncoder.encode("password"));
		
		MemberAuthority memAuth4 = new MemberAuthority();
		memAuth4.setAuthority(authRepo.findOne(1));
		memAuth4.setMember(entity4);
		
		memberRepo.save(entity4);
	}
}