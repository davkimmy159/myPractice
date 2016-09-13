package kr.co.ps119.service;

import java.util.List;

import kr.co.ps119.entity.MemberUser;

public interface InterfaceService_1 {
	public List<MemberUser> findAll();
	
	public MemberUser save(MemberUser newMemberUser);
	
	public void delete(Long id);
}
