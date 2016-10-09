package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.Member;

@Repository
@Transactional
public interface MemberRepository extends JpaRepository<Member, Long> {

	public Member findByEmail(String email);
	public Member findByUsername(String username);
}
