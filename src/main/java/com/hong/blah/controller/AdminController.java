package com.hong.blah.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hong.blah.config.auth.PrincipalDetail;
import com.hong.blah.model.Board;
import com.hong.blah.model.Reply;
import com.hong.blah.model.Report;
import com.hong.blah.model.User;
import com.hong.blah.service.BoardService;
import com.hong.blah.service.ReportService;
import com.hong.blah.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
public class AdminController {

	@Autowired
	UserService userService;
	@Autowired
	BoardService boardService;
	@Autowired
	ReportService reportService;
	
	@GetMapping({"/admin", "/admin_user"})
	@ApiOperation(value = "관리자페이지(user)", notes = "유저(default), 게시글, 댓글, 신고 관리페이지")
	public String adminUserPage(Model model, @PageableDefault(size=15, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable, String search, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail user) {
		if(user.getUser().getRole().equals("ROLE_ADMIN")) { //유저의 세션Role이 ADMIN이면
			Page<User> userList;
			if(search == null || search.equals("")) {
				userList = null;
				userList = userService.유저목록(pageable);
			} else {
				userList = userService.유저검색목록(pageable, search);
			}
			int nowPage = userList.getPageable().getPageNumber() + 1; // 현재페이지 : 0 에서 시작하기에 1을 더해준다.
			int firstlistpage = 1;
			int lastlistpage = 10;
			boolean listpagecheckflg = false;
			
			// 페이지 번호 리스트틀 10개씩 출력하도록 한다.
			// 마지막 리스트가 10개 미만일 경우는 남은 번호만 출력하도록 한다.
			while (listpagecheckflg == false) {
				if (userList.getTotalPages() == 0) {
					lastlistpage = 1;
					listpagecheckflg = true;
				}
				if (lastlistpage > userList.getTotalPages()) {
					lastlistpage = userList.getTotalPages();
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
			model.addAttribute("totalpagesize", userList.getTotalPages());
			// 페이지 번호 리스트 (첫)
			model.addAttribute("firstlistpage", firstlistpage);
			// 페이지 번호 리스트 (마지막)
			model.addAttribute("lastlistpage", lastlistpage);
			// 페이지, 게시글 정보
			model.addAttribute("list",userList);
			model.addAttribute("type", "admin_user");
			return "admin/index";
		} else { // 유저의 세션 Role이 ADMIN이 아니면
			model.addAttribute("code", "403");
			return "errors/error";
		}
	}
	
	@GetMapping("/admin_board")
	@ApiOperation(value = "관리자페이지(board)", notes = "유저(default), 게시글, 댓글, 신고 관리페이지")
	public String adminBoardPage(Model model, @PageableDefault(size=15, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable, String search, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail user) {
		if(user.getUser().getRole().equals("ROLE_ADMIN")) { //유저의 세션Role이 ADMIN이면
			Page<Board> boardList;
			if(search == null || search.equals("")) {
				boardList = boardService.글목록(pageable);
			} else {
				boardList = boardService.글검색목록(pageable, search);
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
			model.addAttribute("list",boardList);
			model.addAttribute("type", "admin_board");
			return "admin/index";
		}else {
			model.addAttribute("code", "403");
			return "errors/error";
		}
	}
	
	@GetMapping("/admin_reply")
	@ApiOperation(value = "관리자페이지(reply)", notes = "유저(default), 게시글, 댓글, 신고 관리페이지")
	public String adminReplyPage(Model model, @PageableDefault(size=5, sort="createdAt", direction=Sort.Direction.DESC) Pageable pageable, String search, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail user) {
		if(user.getUser().getRole().equals("ROLE_ADMIN")) { //유저의 세션Role이 ADMIN이면
			Page<Reply> replyList;
			if(search == null) {
				replyList = boardService.댓글목록(pageable);
			} else {
				replyList = boardService.댓글검색목록(pageable, search);
			}
			int nowPage = replyList.getPageable().getPageNumber() + 1; // 현재페이지 : 0 에서 시작하기에 1을 더해준다.
			int firstlistpage = 1;
			int lastlistpage = 10;
			boolean listpagecheckflg = false;
			
			// 페이지 번호 리스트틀 10개씩 출력하도록 한다.
			// 마지막 리스트가 10개 미만일 경우는 남은 번호만 출력하도록 한다.
			while (listpagecheckflg == false) {
				if (replyList.getTotalPages() == 0) {
					lastlistpage = 1;
					listpagecheckflg = true;
				}
				if (lastlistpage > replyList.getTotalPages()) {
					lastlistpage = replyList.getTotalPages();
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
			model.addAttribute("totalpagesize", replyList.getTotalPages());
			// 페이지 번호 리스트 (첫)
			model.addAttribute("firstlistpage", firstlistpage);
			// 페이지 번호 리스트 (마지막)
			model.addAttribute("lastlistpage", lastlistpage);
			// 페이지, 게시글 정보
			model.addAttribute("list",replyList);
			model.addAttribute("type", "admin_reply");
			return "admin/index";
		} else {
			model.addAttribute("code", "403");
			return "errors/error";
		}
	}
	
	@GetMapping({"/admin_report"})
	@ApiOperation(value = "신고 관리페이지 (처리중)", notes = "유저(default), 게시글, 댓글, 신고 관리페이지")
	public String adminReportDoingPage(Model model, @PageableDefault(size=15) 
												@SortDefault.SortDefaults({
													@SortDefault(sort = "targetType", direction = Sort.Direction.DESC),
													@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
													}) Pageable pageable, String search, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail user) {
		if(user.getUser().getRole().equals("ROLE_ADMIN")) { //유저의 세션Role이 ADMIN이면
			Page<Report> reportList;
			if(search == null || search.equals("")) {
				reportList = null;
				reportList = reportService.신고목록(pageable);
			} else {
				reportList = reportService.신고검색목록(pageable, search);
			}
			int nowPage = reportList.getPageable().getPageNumber() + 1; // 현재페이지 : 0 에서 시작하기에 1을 더해준다.
			int firstlistpage = 1;
			int lastlistpage = 10;
			boolean listpagecheckflg = false;
			
			// 페이지 번호 리스트틀 10개씩 출력하도록 한다.
			// 마지막 리스트가 10개 미만일 경우는 남은 번호만 출력하도록 한다.
			while (listpagecheckflg == false) {
				if (reportList.getTotalPages() == 0) {
					lastlistpage = 1;
					listpagecheckflg = true;
				}
				if (lastlistpage > reportList.getTotalPages()) {
					lastlistpage = reportList.getTotalPages();
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
			model.addAttribute("totalpagesize", reportList.getTotalPages());
			// 페이지 번호 리스트 (첫)
			model.addAttribute("firstlistpage", firstlistpage);
			// 페이지 번호 리스트 (마지막)
			model.addAttribute("lastlistpage", lastlistpage);
			// 페이지, 게시글 정보
			model.addAttribute("list",reportList);
			model.addAttribute("type", "admin_report");
			return "admin/index";
		} else {
			model.addAttribute("code", "403");
			return "errors/error";
		}
	}
	
	@GetMapping({"/admin_processed"})
	@ApiOperation(value = "신고 관리페이지 (처리중)", notes = "유저(default), 게시글, 댓글, 신고 관리페이지")
	public String adminReportDonePage(Model model, @PageableDefault(size=15) 
												@SortDefault.SortDefaults({
													@SortDefault(sort = "targetType", direction = Sort.Direction.DESC),
													@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
													}) Pageable pageable, String search, HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail user) {
		if(user.getUser().getRole().equals("ROLE_ADMIN")) { //유저의 세션Role이 ADMIN이면
			Page<Report> reportList;
			if(search == null || search.equals("")) {
				reportList = null;
				reportList = reportService.신고목록(pageable);
			} else {
				reportList = reportService.신고검색목록(pageable, search);
			}
			int nowPage = reportList.getPageable().getPageNumber() + 1; // 현재페이지 : 0 에서 시작하기에 1을 더해준다.
			int firstlistpage = 1;
			int lastlistpage = 10;
			boolean listpagecheckflg = false;
			
			// 페이지 번호 리스트틀 10개씩 출력하도록 한다.
			// 마지막 리스트가 10개 미만일 경우는 남은 번호만 출력하도록 한다.
			while (listpagecheckflg == false) {
				if (reportList.getTotalPages() == 0) {
					lastlistpage = 1;
					listpagecheckflg = true;
				}
				if (lastlistpage > reportList.getTotalPages()) {
					lastlistpage = reportList.getTotalPages();
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
			model.addAttribute("totalpagesize", reportList.getTotalPages());
			// 페이지 번호 리스트 (첫)
			model.addAttribute("firstlistpage", firstlistpage);
			// 페이지 번호 리스트 (마지막)
			model.addAttribute("lastlistpage", lastlistpage);
			// 페이지, 게시글 정보
			model.addAttribute("list",reportList);
			model.addAttribute("type", "admin_processed");
			return "admin/index";
		} else {
			model.addAttribute("code", "403");
			return "errors/error";
		}
	}
	
	@GetMapping("/admin/{target_type}/updateForm/{id}")
	@ApiOperation(value = "관리자 수정 페이지(팝업)")
	public String adminUserUpdateForm(@PathVariable String target_type, @PathVariable String id, Model model, @AuthenticationPrincipal PrincipalDetail user) {
		if(user.getUser().getRole().equals("ROLE_ADMIN")) { //유저의 세션Role이 ADMIN이면
			if(target_type.equals("user")) {
				model.addAttribute("user", userService.유저상세(id));
			}
			if(target_type.equals("board")) {
				model.addAttribute("board", boardService.글상세보기(id));
			}
			if(target_type.equals("reply")) {
				int a = Integer.parseInt(id);
				model.addAttribute("reply", boardService.댓글상세보기(a));
			}
			if(target_type.equals("report")) {
				int a = Integer.parseInt(id);
				model.addAttribute("report", reportService.신고상세보기(a));
			}
			model.addAttribute("target_type", target_type);
			return "admin/updateForm";
		} else {
			model.addAttribute("code", "403");
			return "errors/error";
		}
	}
	
}
