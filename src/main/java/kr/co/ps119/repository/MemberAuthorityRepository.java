package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.MemberAuthority;

@Repository
@Transactional
public interface MemberAuthorityRepository extends JpaRepository<MemberAuthority, Long> {

}
