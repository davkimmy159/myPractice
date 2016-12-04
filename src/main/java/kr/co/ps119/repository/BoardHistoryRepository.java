package kr.co.ps119.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ps119.entity.BoardHistory;

public interface BoardHistoryRepository extends JpaRepository<BoardHistory, Long> {
	
	public List<BoardHistory> findByMemberId(Long memberId);
	public List<BoardHistory> findByMemberId(Long memberId, Pageable pageable);
	
	public List<BoardHistory> findByBoardId(Long boardId);
	public List<BoardHistory> findByBoardId(Long boardId, Pageable pageable);
	
	public List<BoardHistory> findByIdInAndMemberId(List<Long> id, Long memberId);
	public List<BoardHistory> findByIdInAndBoardId(List<Long> id, Long boardId);
	
	public Long countByMemberId(Long memberId);
	public Long countByBoardId(Long memberId);
	
	public List<BoardHistory> deleteByIdInAndMemberId(List<Long> id, Long memberId);
	public List<BoardHistory> deleteByIdInAndBoardId(List<Long> id, Long boardId);
}
