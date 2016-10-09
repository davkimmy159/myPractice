package kr.co.ps119.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ps119.entity.Comment;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
