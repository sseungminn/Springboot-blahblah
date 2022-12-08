package com.hong.blah.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

	private Map<String, Object> attributes;
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("sub");
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}

	@Override
	public String getNickName() {
		return (String) attributes.get("given_name");
	}

	@Override
	public String getGender() {
		return (String) attributes.get("gender");
	}

	@Override
	public String getAge_range() {
		return (String) attributes.get("age_range");
	}

}
