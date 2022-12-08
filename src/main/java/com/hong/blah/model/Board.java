package com.hong.blah.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name="board")
public class Board {
	
	@Id
	@Column(length = 100)
	private String id; //게시글(토론방)id
	
	@Column(nullable = false, length = 100)
	private String title; //게시글 제목(토론방 주제)
	
	@Column(nullable = false)
	@Lob //대용량 데이터 (썸머노트 사용하면 html태그 섞여서 용량 커짐)
	private String content; // 게시글 내용(토론 설명 및 자료)
	
	private Timestamp deadline; // 투표마감일
	
//	@Enumerated(EnumType.STRING)
//	private RoomState state; // 상태(투표중, 마감됨)
	
	private int vote_cnt; // 투표수
	
	private int view_cnt; // 조회수
	
	@ManyToOne(fetch = FetchType.EAGER) //Many=Board, One=User
	@JoinColumn(name="user_id") //Board테이블 생성시 user_id컬럼 생김
	private User user;
	
	// mappedBy 연관관계의 주인이 아님 (난 FK가 아니에요) DB에 컬럼 만들지 마세요.
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // 무조건 댓글 가져와야함 EAGER, cascade REMOVE 게시글 지울때 속한 댓글도 지움
	@JsonIgnoreProperties({"board"}) //board <-> reply 무한참조방지
	@OrderBy("createdAt asc")
	private List<Reply> comments;
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // 무조건 댓글 가져와야함 EAGER, cascade REMOVE 게시글 지울때 속한 댓글도 지움
	@JsonIgnoreProperties({"board"}) //board <-> reply 무한참조방지
	@OrderBy("createdAt asc")
	private List<Reply> replys;
		
	@CreationTimestamp // 시간 자동 입력
	private Timestamp createdAt; // 글생성시간
	
}
