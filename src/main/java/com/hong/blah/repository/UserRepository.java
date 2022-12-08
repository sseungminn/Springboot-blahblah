package com.hong.blah.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hong.blah.model.User;

// DAO
// 자동으로 bean 등록이 됨
//@Repository //생략가능
public interface UserRepository extends JpaRepository<User, String>{
	
	// SELECT * FROM user WHERE id = ?
//	Optional<User> findById(String id);
	@Override
	Optional<User> findById(String id);
	User findByEmail(String email);
	User findByUsername(String username);
	Page<User> findByIdOrEmailContaining(Pageable pageable, String id, String email);
}


//JPA Naming 쿼리
	// SELECT * FROM user WHERE username = ? AND password = ?
	//User findByUsernameAndPassword(String username, String password);

//	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	User login(String username, String password);