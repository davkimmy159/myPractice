package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ps119.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
