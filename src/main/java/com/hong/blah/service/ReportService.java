package com.hong.blah.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hong.blah.model.Report;
import com.hong.blah.repository.ReportRepository;

@Service
public class ReportService {

	@Autowired
	private ReportRepository reportRepository;
	
	@Transactional
	public void 신고(Report report) {
		report.setContent(report.getContent());
		report.setReportCode(report.getReportCode());
		report.setTargetId(report.getTargetId());
		report.setTargetType(report.getTargetType());
		report.setStatus("처리중");
		reportRepository.save(report);
	}
	
	@Transactional
	public void 신고처리(int id, Report requestReport) {
		Report report = reportRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		report.setStatus("처리됨");
		// 해당 함수 종료시 (Service가 종료될 때) 트랜잭션이 종료됨 -> 더티체킹 발생 -> 자동 업데이트 -> DB flush(커밋)
	}
	
	@Transactional(readOnly = true)
	public Page<Report> 신고목록(Pageable pageable){
		return reportRepository.findAll(pageable);
	}
	@Transactional(readOnly = true)
	public Page<Report> 신고검색목록(Pageable pageable, String search) {
		return reportRepository.findByTargetType(pageable, search);
	}
	@Transactional(readOnly = true)
	public Report 신고상세보기(int id) {
		return reportRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("신고 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 신고삭제하기(int id) {
		reportRepository.deleteById(id);
	}
}
