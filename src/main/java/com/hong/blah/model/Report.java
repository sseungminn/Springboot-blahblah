package com.hong.blah.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //신고번호
	
	@Column(name = "target_id", length = 100)
	private String targetId;
	
	@Column(name = "target_type", length = 10)
	private String targetType;
	
	@Column(name = "report_code", length = 20)
	private String reportCode;
	
	@Column(length = 100)
	private String content;
	
	@Column(length = 20)
	private String status;
	
	@CreationTimestamp
	private Timestamp createdAt;
}
