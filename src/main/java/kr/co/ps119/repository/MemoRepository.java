package kr.co.ps119.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.Board;
import kr.co.ps119.entity.Memo;

@Repository
@Transactional
public interface MemoRepository extends JpaRepository<Memo, Long> {

	public List<Memo> findByBoardId(Long boardId, Pageable pageable);
	public Long countByBoardId(Long boardId);
}
