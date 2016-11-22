package kr.co.ps119.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Member;
import kr.co.ps119.entity.Memo;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.repository.MemoRepository;

@Service
public class MemoService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private MemoRepository memoRepo;
	
	public Memo findOne(Long memoId) {
		return memoRepo.findOne(memoId);
	}
	
	public void deleteById(Long memoId) {
		memoRepo.delete(memoId);
	}
	
	public Long getMemberIdByUsername(String username) {
		Member member = memberRepo.findByUsername(username);
		return member.getId();
	}
	
	public Long getTotalCountOfMemos(Long boardId) {
		Long total = memoRepo.countByBoardId(boardId);

		return total;
	}
	
	public List<Memo> findAllMemosWithPageRequest(Long boardId, Pageable pageable) {
		List<Memo> boardList = memoRepo.findByBoardId(boardId, pageable);
		
		return boardList;
	}
	
	public Memo saveOneMemo(Memo memo) {
		return memoRepo.save(memo);
	}
}