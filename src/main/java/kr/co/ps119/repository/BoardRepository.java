package kr.co.ps119.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.Board;

@Repository
@Transactional
public interface BoardRepository extends JpaRepository<Board, Long> {

	public List<Board> findByMemberId(Long memberId);
	public List<Board> findByMemberId(Long memberId, Pageable pageable);
	public List<Board> findByIdInAndMemberId(List<Long> id, Long memberId);
	
	public Long countByMemberId(Long memberId);
	
	public List<Board> deleteByIdInAndMemberId(List<Long> id, Long memberId);

	public List<Board> findByTitleLike(String title, Pageable pageable);
	public List<Board> findByMemberIdAndTitleLike(Long memberId, String title, Pageable pageable);
	
	public Long countByTitleLike(String title);
	public Long countByMemberIdAndTitleLike(Long memberId, String title);
}