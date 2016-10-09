package kr.co.ps119.config.customizing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import kr.co.ps119.entity.Authority;
import kr.co.ps119.entity.Member;
import kr.co.ps119.repository.MemberRepository;

public class CustomUserDetailsService implements UserDetailsService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepo.findByUsername(username);
		
		List<Authority> perms = member.getMemberAuthorities()
									  .stream()
									  .map(memAuth -> memAuth.getAuthority())
									  .collect(Collectors.toList());
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		perms.stream()
			 .forEach((perm) -> authorities.add(new SimpleGrantedAuthority(perm.getAuthority())));
		
		return new User(member.getEmail(), member.getPassword(), authorities);
	}
}
