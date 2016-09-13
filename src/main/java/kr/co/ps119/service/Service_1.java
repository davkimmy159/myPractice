package kr.co.ps119.service;

import kr.co.ps119.entity.MemberUser;
import kr.co.ps119.repository.MemberUserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Service_1 implements InterfaceService_1{

	@Autowired
	MemberUserRepository mRepo;

	public List<MemberUser> findAll() {
		return mRepo.findAll();
	}
	
	public MemberUser save(MemberUser newMemberUser) {
		return mRepo.save(newMemberUser);
	}
	
	public void delete(Long id) {
		mRepo.delete(id);
	}
}
