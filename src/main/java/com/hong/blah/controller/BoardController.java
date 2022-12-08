package com.hong.blah.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hong.blah.model.Board;
import com.hong.blah.service.BoardService;

import io.swagger.annotations.ApiOperation;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"", "/", "/voting"})
	@ApiOperation(value = "투표중인 방", notes = "투표중인 글 목록 조회")
	public String index(Model model, @PageableDefault(size=5, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable, String search, HttpServletRequest request) {
		Page<Board> boardList;
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
		String todayTemp = sDate.format(new Date());
		Date today = null;
		try {
			today = sDate.parse(todayTemp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = "voting";
		if(search == null || search.equals("")) {
//			boardList = boardService.글목록(pageable);
			boardList = boardService.투표중인글목록(pageable, today);
		} else {
			boardList = boardService.투표중인글검색목록(pageable, today, search);
		}
		int nowPage = boardList.getPageable().getPageNumber() + 1; // 현재페이지 : 0 에서 시작하기에 1을 더해준다.
		int firstlistpage = 1;
		int lastlistpage = 10;
		boolean listpagecheckflg = false;
		
		// 페이지 번호 리스트틀 10개씩 출력하도록 한다.
		// 마지막 리스트가 10개 미만일 경우는 남은 번호만 출력하도록 한다.
		while (listpagecheckflg == false) {
			if (boardList.getTotalPages() == 0) {
				lastlistpage = 1;
				listpagecheckflg = true;
			}
			if (lastlistpage > boardList.getTotalPages()) {
				lastlistpage = boardList.getTotalPages();
			}
			if (nowPage >= firstlistpage && nowPage <= lastlistpage) {
				listpagecheckflg = true;
			} else {
				firstlistpage += 10;
				lastlistpage += 10;
			}
		}
		
		// 현재 페이지 번호
		model.addAttribute("nowlistpageno", nowPage);
		// 총 페이지
		model.addAttribute("totalpagesize", boardList.getTotalPages());
		// 페이지 번호 리스트 (첫)
		model.addAttribute("firstlistpage", firstlistpage);
		// 페이지 번호 리스트 (마지막)
		model.addAttribute("lastlistpage", lastlistpage);
		// 페이지, 게시글 정보
		model.addAttribute("boards",boardList);
		model.addAttribute("today", todayTemp);
		model.addAttribute("type", type);
		return "index";
	}
	
	@GetMapping("/voted")
	@ApiOperation(value = "투표마감된 방", notes = "투표마감된 글 목록 조회")
	public String voted(Model model, @PageableDefault(size=5, sort="deadline", direction=Sort.Direction.DESC) Pageable pageable, String search, HttpServletRequest request) {
		Page<Board> boardList;
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
		String todayTemp = sDate.format(new Date());
		Date today = null;
		try {
			today = sDate.parse(todayTemp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String type = "voted";
		if(search == null || search.equals("")) {
//			boardList = boardService.글목록(pageable);
			boardList = boardService.마감된글목록(pageable, today);
		} else {
			boardList = boardService.마감된글검색목록(pageable, today, search);
		}
		int nowPage = boardList.getPageable().getPageNumber() + 1; // 현재페이지 : 0 에서 시작하기에 1을 더해준다.
		int firstlistpage = 1;
		int lastlistpage = 10;
		boolean listpagecheckflg = false;
		
		// 페이지 번호 리스트틀 10개씩 출력하도록 한다.
		// 마지막 리스트가 10개 미만일 경우는 남은 번호만 출력하도록 한다.
		while (listpagecheckflg == false) {
			if (boardList.getTotalPages() == 0) {
				lastlistpage = 1;
				listpagecheckflg = true;
			}
			if (lastlistpage > boardList.getTotalPages()) {
				lastlistpage = boardList.getTotalPages();
			}
			if (nowPage >= firstlistpage && nowPage <= lastlistpage) {
				listpagecheckflg = true;
			} else {
				firstlistpage += 10;
				lastlistpage += 10;
			}
		}
		
		// 현재 페이지 번호
		model.addAttribute("nowlistpageno", nowPage);
		// 총 페이지
		model.addAttribute("totalpagesize", boardList.getTotalPages());
		// 페이지 번호 리스트 (첫)
		model.addAttribute("firstlistpage", firstlistpage);
		// 페이지 번호 리스트 (마지막)
		model.addAttribute("lastlistpage", lastlistpage);
		// 페이지, 게시글 정보
		model.addAttribute("boards",boardList);
		model.addAttribute("today", todayTemp);
		model.addAttribute("type", type);
		return "index";
	}
	
	
	
	@GetMapping("/board/saveForm")
	@ApiOperation(value = "토론방 생성 폼", notes = "주제(제목), 내용, 마감일 입력받음")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("/auth/board/{id}")
	@ApiOperation(value = "토론방 상세", notes = "방정보, 댓글리스트 조회 및 댓글작성 폼 출력")
	public String detail(@PathVariable String id, Model model, HttpServletRequest request, HttpServletResponse response) {
		Date today = new Date();
		
		boardService.조회수증가(id, request, response);
		model.addAttribute("board", boardService.글상세보기(id));
		model.addAttribute("today", today);
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	@ApiOperation(value = "토론방 수정 폼", notes = "주제(제목), 내용, 마감일 수정 폼")
	public String updateForm(@PathVariable String id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
}
