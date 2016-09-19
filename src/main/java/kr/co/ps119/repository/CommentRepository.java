package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.ps119.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
