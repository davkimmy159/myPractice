package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.Authority;

@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	
}
