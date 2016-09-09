package kr.co.ps119.service;

import kr.co.ps119.data.repository.MemberUserRepository;
import kr.co.ps119.data.entity.MemberUser;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class DemoService {

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
