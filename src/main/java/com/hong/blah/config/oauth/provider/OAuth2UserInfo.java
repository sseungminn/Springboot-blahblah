package com.hong.blah.config.oauth.provider;

public interface OAuth2UserInfo {
//	String getId();
//	String getUsername();
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
	String getNickName();
	String getGender();
	String getAge_range();
}
