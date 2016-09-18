package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ps119.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
