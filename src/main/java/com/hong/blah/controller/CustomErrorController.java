package com.hong.blah.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController{
	
	private String ERROR_TEMPLATES_PATH = "errors/error";
	
	@GetMapping("/error")
	public String handleError(HttpServletRequest request) {
		return ERROR_TEMPLATES_PATH;
	}
	
}
