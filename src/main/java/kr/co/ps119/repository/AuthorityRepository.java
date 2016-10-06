package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ps119.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
	
}
