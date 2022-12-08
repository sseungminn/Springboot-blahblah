package com.hong.blah.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.hong.blah.config.auth.PrincipalDetail;
import com.hong.blah.config.oauth.provider.GoogleUserInfo;
import com.hong.blah.config.oauth.provider.NaverUserInfo;
import com.hong.blah.config.oauth.provider.OAuth2UserInfo;
import com.hong.blah.model.User;
import com.hong.blah.repository.UserRepository;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Value("${hong.key}")
	private String hongKey;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	// 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
	@SuppressWarnings({ "unchecked", "rawtypes", "unused"})
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//		System.out.println("userRequest : " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인했는지 알 수 있음
		OAuth2User oauth2User = super.loadUser(userRequest);
		// userRequest정보
		// 구글 로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code리턴(인증완료) -> AccessToken요청
		// userRequest 정보로 -> loadUser함수 호출-> 구글한테 회원프로필 받음
//		System.out.println("loadUser(userRequest).getAttributes() : " + oauth2User.getAttributes());
		
		OAuth2UserInfo oAuth2UserInfo = null;
		if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			System.out.println(userRequest.getClientRegistration());
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		}else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
		}
		else {
			System.out.println("우리는 구글과 네이버만 지원합니다.");
		}
		System.out.println(oauth2User);
		String provider = oAuth2UserInfo.getProvider(); // google, facebook
		String providerId = oAuth2UserInfo.getProviderId();
		
		String email = oAuth2UserInfo.getEmail();
		String id = UUID.randomUUID().toString().replace("-", "");
		String password = bCryptPasswordEncoder.encode(hongKey);
		String nickname = (oAuth2UserInfo.getNickName() != null ? oAuth2UserInfo.getNickName() : "");
		String age_range = (oAuth2UserInfo.getAge_range() != null ? oAuth2UserInfo.getAge_range() : "");
		String gender = (oAuth2UserInfo.getGender() != null ? oAuth2UserInfo.getGender() : "");
		if(gender.equals("m") || gender.equals("M")) {
			gender = "male";
		}
		if(gender.equals("f") || gender.equals("F")) {
			gender = "female";
		}
		User userEntity = userRepository.findByEmail(email);
		System.out.println(oAuth2UserInfo);
		if(userEntity == null) {
			userEntity = User.builder()
					.id(id)
					.username(id)
					.password(password)
					.oauth(provider)
					.email(email)
					.nickname(nickname)
					.age_range(age_range)
					.gender(gender)
					.notice_yn("Y")
					.role("ROLE_USER")
					.build();
			userRepository.save(userEntity);
		}else {
		}
		if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
//			if(id == userRepository.findByEmail(email).getId()) {//방금 가입한 사람이면
				return new PrincipalDetail(userEntity, (Map)oauth2User.getAttributes().get("response"));
//			} else {
//				return null;
//			}
		}else {
//			if(id == userRepository.findByEmail(email).getId()) {//방금 가입한 사람이면
				return new PrincipalDetail(userEntity, oauth2User.getAttributes());
//			} else {
//				return null;
//			}
		}
	}
	
}

