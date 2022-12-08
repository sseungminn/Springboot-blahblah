package com.hong.blah.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hong.blah.dto.ResponseDto;
import com.hong.blah.model.Report;
import com.hong.blah.service.ReportService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ReportApiController {

	@Autowired
	private ReportService reportService;
	
	@PostMapping("/report/api/{target_type}")
	@ApiOperation(value = "신고")
	public ResponseDto<Integer> boardReport(@PathVariable String target_type, @RequestBody Report report) {
		reportService.신고(report);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/report/api/{id}")
	@ApiOperation(value = "신고처리")
	public ResponseDto<Integer> boardReport(@PathVariable int id, @RequestBody Report report) {
		reportService.신고처리(id, report);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/report/api/{id}")
	@ApiOperation(value = "신고삭제")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		
		reportService.신고삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
