package kr.co.ps119.data.repository;

import kr.co.ps119.data.entity.MemberUser;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MemberUserRepository extends JpaRepository<MemberUser, Long> {
	
}
