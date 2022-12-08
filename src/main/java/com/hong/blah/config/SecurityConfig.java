package com.hong.blah.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hong.blah.config.auth.PrincipalDetailService;
import com.hong.blah.config.oauth.PrincipalOauth2UserService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// 아래 어노테이션 3개는 세트임
@SuppressWarnings("deprecation")
@Configuration // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것(IoC 관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. 컨트롤러로 들어가기 전에 여기서 필터적용됨
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // IoC가 됨
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채는데
		// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
		// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
		http.headers().frameOptions().disable(); // X-Frame-Options 설정 끄기
		http.authorizeRequests()
				.antMatchers("/", "/voting", "/voted", "/auth/**", "/js/**", "/css/**", "/image/**").permitAll() // 해당 경로로 들어오는건 누구나 허용
				.anyRequest()	// /auth말고 다른 경로는 
				.authenticated() // 인증 필요함
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 오는 로그인을 가로챈다
				.loginProcessingUrl("/login") 
				.defaultSuccessUrl("/") // 성공하면 /로 이동
			.and()
				.oauth2Login()
				.loginPage("/auth/loginForm")
				.userInfoEndpoint()
				.userService(principalOauth2UserService);
	}
}
