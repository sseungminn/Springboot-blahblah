package com.hong.blah.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hong.blah.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	Page<Board> findByTitleContaining(Pageable pageable, String search);
	Optional<Board> findById(String id);
	Optional<Board> deleteById(String id);
	Date today = new Date();
	Page<Board> findByDeadlineGreaterThan(Pageable pageable, Date today);
	Page<Board> findByDeadlineGreaterThanAndTitleContaining(Pageable pageable, Date today, String search);
	Page<Board> findByDeadlineLessThanEqual(Pageable pageable, Date today);
	Page<Board> findByDeadlineLessThanEqualAndTitleContaining(Pageable pageable, Date today, String search);
	ArrayList<Board> findByUser_id(String user_id);
	
	@Modifying
	@Query("UPDATE Board b set b.view_cnt = b.view_cnt +1 WHERE b.id = :id")
	int updateView_cnt(@Param(value = "id") String id);
	
	@Modifying
	@Query("UPDATE Board b set b.vote_cnt = b.vote_cnt +1 WHERE b.id = :id")
	int addVote_cnt(String id);
	
	@Modifying
	@Query("UPDATE Board b set b.vote_cnt = b.vote_cnt -1 WHERE b.id = :id")
	int abtractVote_cnt(String id);
}
