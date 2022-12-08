package com.hong.blah.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hong.blah.model.User;
import com.hong.blah.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 -> IoC를 해준다 -> 메모리에 대신 띄워준다
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public Page<User> 유저목록(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	@Transactional(readOnly = true)
	public Page<User> 유저검색목록(Pageable pageable, String search) {
		return userRepository.findByIdOrEmailContaining(pageable, search, search);
	}
	@Transactional(readOnly = true)
	public User 유저상세(String id){
		return userRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("회원 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	@Transactional(readOnly = true)
	public User 이메일로회원찾기(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		String id = UUID.randomUUID().toString().replace("-", "");
		user.setId(id);
		user.setUsername(id);
		user.setPassword(encPassword);
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(String id, User requestUser) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌
		User user = userRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("회원찾기 실패");
				});
		// Validate 체크 -> 성별,연령대 필드에 값이 없으면 수정 가능
		user.setNickname(requestUser.getNickname());
		user.setGender(requestUser.getGender());
		user.setAge_range(requestUser.getAge_range());
		if(requestUser.getRole() != null) {
			user.setRole(requestUser.getRole());
		}
		// 회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션 종료 = commit 자동수행됨
		// 영속화 된 persistance 객체의 변화가 감지되면(더티체킹) update문을 날려줌
	}
	
	@Transactional
	public void 회원삭제(String id) {
		userRepository.deleteById(id);
	}
	
	@Transactional
	public void 관리자회원수정(String id, User requestUser) {
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User 오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌
		User user = userRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("회원찾기 실패");
				});
		// Validate 체크 -> 성별,연령대 필드에 값이 없으면 수정 가능
		if(requestUser.getRole().equals("ROLE_ADMIN")) {
			user.setNickname(requestUser.getNickname()+"👻");
		}else {
			user.setNickname(requestUser.getNickname().replace("👻", ""));
		}
		user.setRole(requestUser.getRole());
		// 회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션 종료 = commit 자동수행됨
		// 영속화 된 persistance 객체의 변화가 감지되면(더티체킹) update문을 날려줌
	}
	
}
