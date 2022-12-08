package com.hong.blah.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class KakaoProfile {
	public Long id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;
	
	@Data
	public class Properties {
		public String nickname;
		public String profile_image;
		public String thumbnail_image;
	}

	@Data
	public class KakaoAccount {
		public Boolean profile_nickname_needs_agreement;
		public Boolean profile_image_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public Boolean is_default_image;
		public String email;
		
		public Boolean gender_needs_agreement;
		public Boolean has_gender;
		public String gender;
		
		public Boolean age_range_needs_agreement;
		public Boolean has_age_range;
		public String age_range;

		@Data
		public class Profile {
			public String nickname;
			public String profile_image_url;
			public String thumbnail_image_url;
			public Boolean is_default_image;
		}
	}

}
