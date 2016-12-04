package kr.co.ps119.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.ps119.entity.BoardHistory;
import kr.co.ps119.entity.Memo;
import kr.co.ps119.repository.BoardHistoryRepository;
import kr.co.ps119.repository.BoardRepository;
import kr.co.ps119.repository.MemberRepository;
import kr.co.ps119.repository.MemoRepository;

@Service
@Transactional
public class BoardHistoryService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private MemoRepository memoRepo;
	
	@Autowired
	private BoardHistoryRepository boardHisRepo;
	
	public long getTotalCountOfHistories(Long boardId) {
		Long total = boardHisRepo.countByBoardId(boardId);
		
		return total;
	}
	
	public List<BoardHistory> findAllHistoriesWithPageRequest(Long boardId, Pageable pageable) {
		List<BoardHistory> historyList = boardHisRepo.findByBoardId(boardId, pageable);
		
		return historyList;
	}
}
