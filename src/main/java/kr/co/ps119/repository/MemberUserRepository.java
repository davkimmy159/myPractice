package kr.co.ps119.repository;

import org.springframework.stereotype.Repository;

import kr.co.ps119.entity.MemberUser;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MemberUserRepository extends JpaRepository<MemberUser, Long> {
	
}
