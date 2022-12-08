package com.hong.blah.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hong.blah.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer>{
	Page<Report> findByTargetType(Pageable pageable, String search);
	Optional<Report> deleteById(int id);
}
