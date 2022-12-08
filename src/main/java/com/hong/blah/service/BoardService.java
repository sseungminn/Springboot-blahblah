package com.hong.blah.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hong.blah.dto.ReplySaveRequestDto;
import com.hong.blah.model.Board;
import com.hong.blah.model.Reply;
import com.hong.blah.model.User;
import com.hong.blah.repository.BoardRepository;
import com.hong.blah.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // @Autowired없애고, final 붙이면 생성될때 초기화해줌
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	@Transactional
	public void 글쓰기(Board board, User user) {
		board.setId(UUID.randomUUID().toString().replace("-", ""));
		board.setVote_cnt(0);
		board.setView_cnt(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	@Transactional(readOnly = true)
	public ArrayList<Board> 회원이생성한토론목록(String user_id){
		return boardRepository.findByUser_id(user_id);
	}
	@Transactional(readOnly = true)
	public Page<Board> 글검색목록(Pageable pageable, String search) {
		return boardRepository.findByTitleContaining(pageable, search);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 투표중인글목록(Pageable pageable, Date today) {
		return boardRepository.findByDeadlineGreaterThan(pageable, today);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 투표중인글검색목록(Pageable pageable, Date today, String search) {
		return boardRepository.findByDeadlineGreaterThanAndTitleContaining(pageable, today, search);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 마감된글목록(Pageable pageable, Date today) {
		return boardRepository.findByDeadlineLessThanEqual(pageable, today);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 마감된글검색목록(Pageable pageable, Date today, String search) {
		return boardRepository.findByDeadlineLessThanEqualAndTitleContaining(pageable, today, search);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(String id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public int 조회수증가(String id, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		boolean checkCookie = false;
		int result = 0;
		if(cookies != null){
            for (Cookie cookie : cookies) {
	            // 이미 조회를 한 경우 체크
	            if (cookie.getName().equals("VIEWCOOKIENAME"+id)) {
	            	checkCookie = true;
	            }
            } if(!checkCookie){
                Cookie newCookie = createCookieForForNotOverlap(id);
                response.addCookie(newCookie);
                result = boardRepository.updateView_cnt(id);
            }
        } else {
            Cookie newCookie = createCookieForForNotOverlap(id);
            response.addCookie(newCookie);
            result = boardRepository.updateView_cnt(id);
        }
		return result;
	}
	
	/*
    * 조회수 중복 방지를 위한 쿠키 생성 메소드
    * @param cookie
    * @return
    * */
    private Cookie createCookieForForNotOverlap(String id) {
        Cookie cookie = new Cookie("VIEWCOOKIENAME"+id, String.valueOf(id));
        cookie.setComment("조회수 중복 증가 방지 쿠키");	// 쿠키 용도 설명 기재
        cookie.setMaxAge(getRemainSecondForTommorow()); 	// 하루를 준다.
        cookie.setHttpOnly(true);				// 서버에서만 조작 가능
        return cookie;
    }

    // 다음 날 정각까지 남은 시간(초)
    private int getRemainSecondForTommorow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tommorow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tommorow, ChronoUnit.SECONDS);
    }
    
	@Transactional
	public int 의견수증가(String id) {
		return boardRepository.addVote_cnt(id);
	}
	
	@Transactional
	public int 의견수감소(String id) {
		return boardRepository.abtractVote_cnt(id);
	}
	
	
	@Transactional
	public void 글삭제하기(String id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(String id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		board.setDeadline(requestBoard.getDeadline());
		// 해당 함수 종료시 (Service가 종료될 때) 트랜잭션이 종료됨 -> 더티체킹 발생 -> 자동 업데이트 -> DB flush(커밋)
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		int maxBundle = 0;
		// 첫 댓글이라면
		if(replyRepository.countComment(replySaveRequestDto.getBoard_id()).equals("0")) {
			replyRepository.commentSave(replySaveRequestDto.getUser_id(), replySaveRequestDto.getBoard_id(), replySaveRequestDto.getContent(), 0, replySaveRequestDto.getOpinion());
		} else {
			maxBundle = 댓글그룹값확인(replySaveRequestDto);
			replyRepository.commentSave(replySaveRequestDto.getUser_id(), replySaveRequestDto.getBoard_id(), replySaveRequestDto.getContent(), maxBundle+1, replySaveRequestDto.getOpinion());
		}
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.commentDelete(replyId);
	}
	
	@Transactional
	public void 답글쓰기(ReplySaveRequestDto replySaveRequestDto) { 
		replyRepository.replySave(replySaveRequestDto.getUser_id(), replySaveRequestDto.getBoard_id(), replySaveRequestDto.getContent(), replySaveRequestDto.getBundle(), replySaveRequestDto.getStep()+1, replySaveRequestDto.getOpinion());
	}
	
	@Transactional(readOnly = true)
	public int 댓글그룹값확인(ReplySaveRequestDto replySaveRequestDto) {
		int maxBundle = Integer.parseInt(replyRepository.maxBundle(replySaveRequestDto.getBoard_id()));
		System.out.println("maxBundle : " + maxBundle);
		if(maxBundle == 0) {
			return 0;
		} else {
			return maxBundle;
		}
	}
	
	
	
	@Transactional
	public void 관리자글수정(String id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시 (Service가 종료될 때) 트랜잭션이 종료됨 -> 더티체킹 발생 -> 자동 업데이트 -> DB flush(커밋)
	}
	
	@Transactional(readOnly = true)
	public Page<Reply> 댓글목록(Pageable pageable){
		return replyRepository.findAll(pageable);
	}
	@Transactional(readOnly = true)
	public Page<Reply> 댓글검색목록(Pageable pageable, String user_id) {
		return replyRepository.findByUser_idContaining(pageable, user_id);
	}
	@Transactional(readOnly = true)
	public Reply 댓글상세보기(int id) {
		return replyRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	@Transactional
	public void 관리자댓글수정(int id, Reply requestReply) {
		Reply reply = replyRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		reply.setContent(requestReply.getContent());
		// 해당 함수 종료시 (Service가 종료될 때) 트랜잭션이 종료됨 -> 더티체킹 발생 -> 자동 업데이트 -> DB flush(커밋)
	}
	@Transactional
	public void 관리자댓글삭제(int reply_id) {
		replyRepository.adminReplyDelete(reply_id);
	}
	
}
