package kr.co.ps119.config.customizing;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.MemberRepository;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken)authentication;
		Member member = memberRepo.findByUsername(authToken.getName());
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
