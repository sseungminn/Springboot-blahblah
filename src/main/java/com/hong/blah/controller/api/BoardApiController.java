package com.hong.blah.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hong.blah.config.auth.PrincipalDetail;
import com.hong.blah.dto.ReplySaveRequestDto;
import com.hong.blah.dto.ResponseDto;
import com.hong.blah.model.Board;
import com.hong.blah.service.BoardService;

import io.swagger.annotations.ApiOperation;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	@ApiOperation(value = "토론방생성")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	@ApiOperation(value = "토론방삭제")
	public ResponseDto<Integer> deleteById(@PathVariable String id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{id}")
	@ApiOperation(value = "토론방수정", notes = "주제(제목), 내용, 마감일 수정 가능")
	public ResponseDto<Integer> update(@PathVariable String id, @RequestBody Board board){
		boardService.글수정하기(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	//댓글작성
	@PostMapping("/api/board/{board_id}/comment")
	@ApiOperation(value = "댓글작성", notes = "comment는 댓글, reply는 답글")
	public ResponseDto<Integer> commentSave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
		boardService.의견수증가(replySaveRequestDto.getBoard_id());
		boardService.댓글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{board_id}/comment/{comment_id}")
	@ApiOperation(value = "댓글삭제", notes = "comment는 댓글, reply는 답글")
	public ResponseDto<Integer> commentDelete(@PathVariable String board_id, @PathVariable int comment_id){
		boardService.댓글삭제(comment_id);
		boardService.의견수감소(board_id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/board/{board_id}/reply/{reply_id}")
	@ApiOperation(value = "답글삭제", notes = "comment는 댓글, reply는 답글")
	public ResponseDto<Integer> replyDelete(@PathVariable String board_id, @PathVariable int reply_id){
		boardService.댓글삭제(reply_id);
		boardService.의견수감소(board_id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	//답글작성
	@PostMapping("/api/board/{board_id}/reply")
	@ApiOperation(value = "답글작성", notes = "comment는 댓글, reply는 답글")
	public ResponseDto<Integer> replySave(@PathVariable String board_id, @RequestBody ReplySaveRequestDto replySaveRequestDto) {
		boardService.의견수증가(board_id);
		boardService.답글쓰기(replySaveRequestDto);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
