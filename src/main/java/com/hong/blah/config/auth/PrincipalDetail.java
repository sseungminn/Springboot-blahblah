package com.hong.blah.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.hong.blah.model.User;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class PrincipalDetail implements UserDetails, OAuth2User{
	
	private User user;
	private Map<String, Object> attributes;
	
	//OAuth2 로그인
	public PrincipalDetail(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	//일반 로그인
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getId();
	}

	public String getNickname() {
		return user.getNickname();
	}
	// 계정이 만료되지 않았는지 리턴함 ( true : 만료 안 됐음)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않았는지 리턴함 ( true : 안 잠겼음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴함 ( true : 만료 안 됐음)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화(사용 가능)된 상태인지 리턴한다. ( true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		return collectors;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}
}
