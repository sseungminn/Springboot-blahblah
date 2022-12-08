package com.hong.blah.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hong.blah.dto.ResponseDto;
import com.hong.blah.model.User;
import com.hong.blah.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController // 데이터만 return 해주는 컨트롤러
public class UserApiController {

	@Value("${hong.key}")
	private String hongKey;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc") // join process
	@ApiOperation(value = "회원가입")
	public ResponseDto<Integer> save(@RequestBody User user) {
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PutMapping("/api/user/{id}")
	@ApiOperation(value = "회원수정", notes = "닉네임, 연령대, 성별 수정 가능")
	public ResponseDto<Integer> update(@PathVariable String id, @RequestBody User user) { // key=value, x-www-form-urlencoded
		userService.회원수정(id, user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션값 변경해줄 것임

		// 세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), hongKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
