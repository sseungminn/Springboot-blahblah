package com.hong.blah.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hong.blah.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	// interface 안에서는 public 생략가능
	// 영속화 필요 없음
	@Modifying
	@Query(value="INSERT INTO Reply(user_id, board_id, content, bundle, step, opinion, createdAt) VALUES(?1, ?2, ?3, ?4, 0, ?5, now())", nativeQuery = true)
	int commentSave(String user_id, String board_id, String content, int bundle, String opinion); // 업데이트된 행의 개수를 리턴해줌
	
	@Modifying
	@Query(value="INSERT INTO Reply(user_id, board_id, content, bundle, step, opinion, createdAt) VALUES(?1, ?2, ?3, ?4, ?5, ?6, now())", nativeQuery = true)
	int replySave(String user_id, String board_id, String content, int bundle, int step, String opinion); // 업데이트된 행의 개수를 리턴해줌
	
	@Query(value="SELECT MAX(bundle) FROM Reply WHERE board_id=?1 and bundle = (SELECT MAX(bundle) FROM Reply WHERE board_id = ?1)", nativeQuery = true)
	String maxBundle(String board_id);
	
	@Query(value="SELECT COUNT(id) FROM Reply WHERE board_id=?1", nativeQuery = true)
	String countComment(String board_id);
	
	@Modifying
	@Query("UPDATE Reply r set r.content = '(사용자에 의해 삭제된 댓글입니다.)', opinion='' WHERE r.id = :id")
	int commentDelete(int id);
	
	@Modifying
	@Query("UPDATE Reply r set r.content = '(관리자에 의해 삭제된 댓글입니다.)', opinion='' WHERE r.id = :id")
	int adminReplyDelete(int id);
	
	Page<Reply> findById(Pageable pageable, Integer id);
	Page<Reply> findByUser_idContaining(Pageable pageable, String user_id);
	
}
