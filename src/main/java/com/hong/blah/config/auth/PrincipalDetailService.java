package com.hong.blah.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hong.blah.model.User;
import com.hong.blah.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service // Bean 등록
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private final UserRepository userRepository;
	
	// 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
	// password 부분 처리는 알아서 해줌
	// username이 DB에 있는지 확인해주면 됨
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User principal = userRepository.findByEmail(email);
		if(principal==null) {
			return null;
		}
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장됨
	}
}
