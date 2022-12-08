package com.hong.blah.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@DynamicInsert // insert시에 null인 필드를 제외시켜준다.(DB상의 default 값으로 넣어줌)
@Data  // getter, setter
@NoArgsConstructor  // 빈생성자
@AllArgsConstructor  //모든 생성자
@Builder
//ORM -> Java(다른언어) Object -> 테이블로 맵핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블 생성이 됨
@Table(name="user")
public class User {

	@Id // Primary Key
	@Column(length = 100)
	private String id;
	
	@Column(length = 100)
	private String username;
	
	@Column(length = 100)
	private String password;
	
	@Column(length = 50)
	private String nickname; // 닉네임
	
	@Column(length = 50)
	private String email; // 이메일(중복 회원가입 확인용)
	
	@Column(length = 10)
	private String oauth; // kakao, naver, google
	
	@Column(length = 1)
	private String notice_yn; //알림여부
	
	@Column(length = 10)
	private String gender; // 성별
	
	@Column(length = 10)
	private String age_range; //연령대
	
	@Column(length = 15)
	private String role;
	
	@CreationTimestamp // 시간 자동 입력
	private Timestamp createdAt; // 유저 가입 시간
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"user"})
	private List<Board> boards;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({"user"})
	private List<Reply> replys;
	
	@Builder
	public User(String id, String username, String password, String oauth, String email, String notice_yn, String gender, String age_range, String role, Timestamp createdAt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.oauth = oauth;
		this.email = email;
		this.notice_yn = notice_yn;
		this.gender = gender;
		this.age_range = age_range;
		this.role = role;
		this.createdAt = createdAt;
	}
	
}
