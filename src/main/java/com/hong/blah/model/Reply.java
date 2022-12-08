package com.hong.blah.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="reply")
public class Reply {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라감
	private int id; // 오라클: 시퀀스, mysql: auto-increment
	
	@Column(nullable = false, length = 200)
	private String content; 
	
	@ManyToOne(fetch = FetchType.EAGER) //여러개의 reply가 하나의 board에
	@JoinColumn(name="board_id")
	private Board board;
	
	@ManyToOne(fetch = FetchType.EAGER) //여러개의 reply를 한명의 user가
	@JoinColumn(name="user_id")
	private User user;
	
	private int bundle; //그룹번호
	
	private int step; //그룹내순서
	
	@Column(length = 10)
	private String opinion; //찬반여부
	
	@CreationTimestamp
	private Timestamp createdAt;

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", user=" + user + 
				", bundle=" + bundle + ", step=" + step + ", createdAt="
				+ createdAt + "]";
	}
	
	
}
