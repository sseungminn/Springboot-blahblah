package com.hong.blah.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hong.blah.config.auth.PrincipalDetail;
import com.hong.blah.model.KakaoProfile;
import com.hong.blah.model.OAuthToken;
import com.hong.blah.model.User;
import com.hong.blah.service.BoardService;
import com.hong.blah.service.UserService;

import io.swagger.annotations.ApiOperation;

// 인증이 안 된 사용자들이 출입할 수 있는 경로를 /auth/** 허용 
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 등등 허용
@Controller
public class UserController {

	private String grant_type = "authorization_code";
	private String client_id = "2762ad5ea7e443f3f9716744680f2689";
//	private String redirect_uri = "http://localhost/auth/kakao/callback";
	private String redirect_uri = "http://ec2-15-165-250-155.ap-northeast-2.compute.amazonaws.com/auth/kakao/callback";
	
	@Value("${hong.key}")
	private String hongKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/auth/loginForm")
	@ApiOperation(value = "로그인 폼", notes = "소셜로그인(카카오, 구글, 네이버) 가능, 일반 로그인 없음")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	@ApiOperation(value = "카카오 로그인 및 회원가입")
	public String kakaoCallback(String code) { // @ResponseBody -> Data를 리턴해주는 컨트롤러 함수

		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		// Retrofit2, OkHttp, RestTemplate 등
		RestTemplate rt = new RestTemplate();

		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", grant_type);
		params.add("client_id", client_id);
		params.add("redirect_uri", redirect_uri);
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답 받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("인가 코드 : " + code);
		System.out.println("카카오 액세스 토큰 : " + oauthToken.getAccess_token());
		System.out.println("oauthToken : " + oauthToken);

		// 11111111111111111111111111111111111111
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		System.out.println(kakaoProfileRequest2);
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
					kakaoProfileRequest2, String.class);
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
//		System.out.println("카카오 아이디 : "+ kakaoProfile.getId());
//		System.out.println("카카오 이메일 : "+ kakaoProfile.getKakao_account().getEmail());
//		System.out.println("카카오 닉네임1 : " + kakaoProfile.getKakao_account().getProfile().getNickname());
//		System.out.println("카카오 닉네임2 : " + kakaoProfile.getProperties().nickname);
//		System.out.println("카카오 닉네임3 : " + kakaoProfile.getKakao_account().getProfile().nickname);
//		System.out.println("DB에 넣을 유저네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
//		System.out.println("DB에 넣을 이메일 : " + kakaoProfile.getKakao_account().getEmail());
//		System.out.println("DB에 넣을 패스워드 : " + hongKey);
		// 2222222222222222222222222222222
		User kakaoUser = User.builder()
							  .password(hongKey)
							  .oauth("kakao")
							  .email(kakaoProfile.getKakao_account().getEmail())
							  .notice_yn("Y")
							  .gender(kakaoProfile.getKakao_account().getGender())
							  .age_range(kakaoProfile.getKakao_account().getAge_range())
							  .role("ROLE_USER")
							  .build();
		// 이미 가입한 사람인지 체크
		User originUser = userService.이메일로회원찾기(kakaoUser.getEmail());
		System.out.println(kakaoProfile);
		if(originUser == null) {
			System.out.println("신규회원입니다. 자동으로 가입 후 로그인됩니다.(카카오)");
			userService.회원가입(kakaoUser);
		}
		// 로그인처리
		try {
			System.out.println("카카오 로그인 요청");
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getEmail(), hongKey));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch (Exception e) {
			throw e;
		}
		return "redirect:/";
	}

	@GetMapping("/user/updateForm")
	@ApiOperation(value = "회원 수정 폼", notes = "닉네임, 연령대, 성별 수정 폼")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal, Model model) {
		model.addAttribute("myBoard", boardService.회원이생성한토론목록(principal.getUser().getId()));
		return "user/updateForm";
	}

	@GetMapping("/user/{id}")
	@ApiOperation(value = "회원 프로필", notes = "회원정보 및 작성한 글")
	public String profile(@PathVariable String id, Model model) {
		Date today = new Date();
		model.addAttribute("profile", userService.유저상세(id));
		model.addAttribute("boards", boardService.회원이생성한토론목록(id));
		model.addAttribute("today", today);
		return "user/profile";
	}
}
