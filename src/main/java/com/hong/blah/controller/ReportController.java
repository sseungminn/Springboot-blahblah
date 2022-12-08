package com.hong.blah.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;

@Controller
public class ReportController {

	@GetMapping({"/report/board/{id}", "/report/reply/{id}", "/report/user/{id}"})
	@ApiOperation(value = "토론방, 댓글 신고 폼(팝업)")
	public String reportForm(@PathVariable String id, @RequestParam String target_type, @RequestParam String board_title, @RequestParam String user_nickname, Model model) {
		model.addAttribute("target_id", id);
		model.addAttribute("target_type", target_type);
		model.addAttribute("board_title", board_title);
		model.addAttribute("user_nickname", user_nickname);
		return "reportForm";
	}
	
}
