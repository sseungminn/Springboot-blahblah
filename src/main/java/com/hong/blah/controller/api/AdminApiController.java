package com.hong.blah.controller.api;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hong.blah.dto.ResponseDto;
import com.hong.blah.model.Board;
import com.hong.blah.model.Reply;
import com.hong.blah.model.User;
import com.hong.blah.service.BoardService;
import com.hong.blah.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
public class AdminApiController {

	@Value("${hong.key}")
	private String hongKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PutMapping("/admin/api/user/{id}")
	@ApiOperation(value = "관리자 회원수정", notes = "닉네임, 권한 수정 가능")
	public ResponseDto<Integer> updateUser(@PathVariable String id, @RequestBody User user, @AuthenticationPrincipal Principal principal) { // key=value, x-www-form-urlencoded
		userService.관리자회원수정(id, user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 직접 세션값 변경해줄 것임

		// 세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), hongKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/admin/api/user/{id}")
	@ApiOperation(value = "관리자 회원삭제")
	public ResponseDto<Integer> deleteUser(@PathVariable String id){
		userService.회원삭제(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/admin/api/board/{id}")
	@ApiOperation(value = "관리자 게시글수정", notes = "제목, 내용 수정 가능")
	public ResponseDto<Integer> updateBoard(@PathVariable String id, @RequestBody Board board) { // key=value, x-www-form-urlencoded
		boardService.관리자글수정(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/admin/api/board/{id}")
	@ApiOperation(value = "관리자 게시글삭제")
	public ResponseDto<Integer> deleteBoard(@PathVariable String id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/admin/api/reply/{id}")
	@ApiOperation(value = "관리자 댓글수정", notes = "내용 수정 가능")
	public ResponseDto<Integer> updateReply(@PathVariable int id, @RequestBody Reply reply) { // key=value, x-www-form-urlencoded
		boardService.관리자댓글수정(id, reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/admin/api/board/{board_id}/reply/{id}")
	@ApiOperation(value = "관리자 댓글삭제")
	public ResponseDto<Integer> deleteReply(@PathVariable String board_id, @PathVariable int id){
		boardService.의견수감소(board_id);
		boardService.관리자댓글삭제(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
