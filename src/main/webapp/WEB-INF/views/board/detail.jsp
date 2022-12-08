<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<script>
function boardReportForm(board_id, target_type, board_title, user_nickname){
	window.open('/report/board/'+board_id+'?target_type='+target_type+'&board_title='+board_title+'&user_nickname='+user_nickname,'board-report-form','width=500, height=400, menubar=no, status=no, toolbar=no');
}
function userReportForm(user_id, target_type, board_title, user_nickname){
	window.open('/report/user/'+user_id+'?target_type='+target_type+'&board_title='+board_title+'&user_nickname='+user_nickname,'user-report-form','width=500, height=400, menubar=no, status=no, toolbar=no');
}
function replyReportForm(reply_id, target_type, reply_content, user_nickname){
	window.open('/report/reply/'+reply_id+'?target_type='+target_type+'&board_title='+reply_content+'&user_nickname='+user_nickname,'reply-report-form','width=500, height=400, menubar=no, status=no, toolbar=no');
}
let page = new URLSearchParams(location.search).get('page');
let search = new URLSearchParams(location.search).get('search');
let type = new URLSearchParams(location.search).get('type');
function list(){
	if(type=='voting'){
		location.href='/voting?page='+page+'&search='+search+'&type='+type;		
	} else if(type == 'voted'){
		location.href='/voted?page='+page+'&search='+search+'&type='+type;
	} else{
		location.href='/';
	}
}
function updateBtn(){
	location.href="/board/${board.id}/updateForm?page="+page+'&search='+search+'&type='+type;
}
function commentDelete(board_id, comment_id){
	check = confirm("삭제하시겠습니까?");
	if(check == true){
		index.commentDelete(board_id, comment_id);
	}
}
function replyDelete(board_id, reply_id){
	check = confirm("삭제하시겠습니까?");
	if(check == true){
		index.replyDelete(board_id, reply_id);
	}
}
function modalOpen(bundle, step) {
	if("${principal}" != null && "${principal}" != ''){
		if("${principal.user.nickname}" == null || "${principal.user.nickname}" == ''){
			check = confirm('닉네임 등록후 이용가능합니다. 등록하시겠습니까?');
			if(check == true){
				location.href="/user/updateForm";
			}else{
				return false;
			}
		}
	}
    var modalPop = $('.modal-wrap');
    var modalBg = $('.modal-bg'); 
    $(modalPop).show();
    $(modalBg).show();
    $("#reply-content").val('');
    $("#reply-text-count").text('');
    $("#reply-content").focus();
    $('#bundle').val(bundle);
    $('#step').val(step);
}

function modalClose() {
   var modalPop = $('.modal-wrap');
   var modalBg = $('.modal-bg');
   $(modalPop).hide();
   $(modalBg).hide();
   $("#comment-content").focus();
//    $('html').removeAttr('style');
}
function nicknameCheck(){
	if("${principal}" != null && "${principal}" != ''){
		if("${principal.user.nickname}" == null || "${principal.user.nickname}" == ''){
			check = confirm('닉네임 등록후 이용가능합니다. 등록하시겠습니까?');
			if(check == true){
				location.href="/user/updateForm";
			}else{
				location.href="/";
			}
		} else{
			index.commentSave();
		}
	}
}
</script>
<br />
<div class="container">
	<button class="btn btn-outline-secondary" onclick="list();">목록</button>
	<c:if test="${board.deadline > today}">
		<c:if test="${board.user.id eq  principal.user.id}">
			<button type="button" onclick="updateBtn()" class="btn btn-outline-warning">수정</button>
			<button id="board-delete" class="btn btn-outline-danger">삭제</button>
		</c:if>
	</c:if>
	<c:if test="${principal ne null && principal ne '' && board.user.role ne 'ADMIN' && board.user.id ne principal.user.id}">
		<button type="button" class="btn btn-outline-danger" onclick="boardReportForm('${board.id}','board','${board.title}','${board.user.nickname }');">신고</button>
	</c:if>

	<div id="id" hidden="true">${board.id}</div>
	<div class="h1 font-italic font-weight-bold">
		<c:out value='"${board.title }"'></c:out>
	</div>
	<div>
		작성자 : <a class="font-weight-bold" href="/user/${board.user.id}"><c:out value="${board.user.nickname}"></c:out></a>
		<c:if test="${principal ne null && principal ne '' && board.user.role ne 'ADMIN' && board.user.id ne principal.user.id}">
			<button type="button" class="btn badge btn-outline-danger" onclick="userReportForm('${board.user.id}','user', '${board.title }','${board.user.nickname }');">신고</button>
		</c:if>
	</div>
	<div>투표시작일 : ${board.createdAt.toString().substring(0,19)}</div>
	<div>투표마감일 : ${board.deadline.toString().substring(0,19)}</div>
	<div>상태 : ${board.deadline > today ? '투표중' : '마감됨'}</div>
	<div>조회수 : ${board.view_cnt}</div>
	<c:set var="agree_cnt" value="0" />
	<c:set var="disagree_cnt" value="0" />
	<c:set var="vote_cnt" value="0" />
	<c:forEach items="${board.comments }" var="list" varStatus="status">
		<c:if test="${list.opinion ne '' }">
			<c:if test="${list.opinion eq 'Agree' }">
				<c:set var="agree_cnt" value="${agree_cnt+1 }" />
			</c:if>
			<c:if test="${list.opinion eq 'Disagree' }">
				<c:set var="disagree_cnt" value="${disagree_cnt+1 }" />
			</c:if>
			<c:set var="vote_cnt" value="${vote_cnt+1}" />
		</c:if>
		<c:if test="${agree_cnt ne 0 || disagree_cnt ne 0 }">
			<c:if test="${status.last}">
				<div>의견수 : ${vote_cnt}</div>
				<div class="charts">
					<div style="border-top-left-radius: 10px; border-bottom-left-radius: 10px; float: left;"
						class="charts__chart chart--p<fmt:formatNumber value="${agree_cnt/(agree_cnt+disagree_cnt)*100}" pattern="#"></fmt:formatNumber> chart--green" data-percent></div>
					<!-- /.charts__chart -->
					<div style="border-top-right-radius: 10px; border-bottom-right-radius: 10px; float: right;"
						class="charts__chart chart--p<fmt:formatNumber value="${disagree_cnt/(agree_cnt+disagree_cnt)*100}" pattern="#"></fmt:formatNumber> chart--red" data-percent></div>
					<!-- /.charts__chart -->
					<span style="float: left;">찬성 ${agree_cnt }</span> <span style="float: right;">반대 ${disagree_cnt }</span>
				</div>
				<br />
				<br />
			</c:if>
		</c:if>
	</c:forEach>
	<hr />
	<div>${board.content }</div>
	<hr />
	<!-- 댓글 -->
	<c:if test="${board.deadline > today && principal ne null && principal ne ''}">
		<!-- 마감됐으면 댓글 못달게 -->
		<div class="card">
			<form>
				<input type="hidden" id="user_id" value="${principal.user.id }"> <input type="hidden" id="board_id" value="${board.id }">
				<!-- comment opinion(찬성, 반대) -->
				<div class="card-header form-check" style="width: 100%;">
					<div class="d-flex justify-content-between opinion">
						<div class="align-self-center">
							<input type="radio" id="agree" name="opinion" value="Agree" /> <label for="agree" style="width: 30vw;">찬성</label>
						</div>
						<div class="align-self-center">
							<input type="radio" id="disagree" name="opinion" value="Disagree" /> <label for="disagree" style="width: 30vw;">반대</label>
						</div>
					</div>
				</div>
				<!-- comment input form -->
				<div class="card-body">
					<textarea id="comment-content" class="form-control" rows="1"></textarea>
					<div id="comment-text-count" class="textCount" style="float: left;"></div>
				</div>
				<!-- comment save button -->
				<div class="card-footer">
					<button type="button" onClick="nicknameCheck();" class="btn btn-primary">등록</button>
				</div>
			</form>
		</div>
	</c:if>
	<br />

	<div class="card">
		<!-- Comment card header -->
		<div class="card-header bg-light">
			<i class="bi-chat-dots"></i> Comments (${board.vote_cnt })<br />
		</div>

		<!-- Comment List -->
		<ul id="reply-box" class="list-group">
			<!-- no comments -->
			<c:if test="${fn:length(board.comments) eq 0}">
				<li class="list-group-item d-flex justify-content-between">등록된 댓글이 없습니다.</li>
			</c:if>

			<c:forEach var="comment" items="${board.comments }">
				<!-- parent comments -->
				<c:if test="${comment.step eq 0 }">
					<li id="comment-${comment.id}" class="list-group-item d-flex justify-content-between" style="padding-right: 5px;"><c:if
							test="${comment.content ne '(사용자에 의해 삭제된 댓글입니다.)' && comment.content ne '(관리자에 의해 삭제된 댓글입니다.)'}">
							<table class="newline">
								<tr class="newline">
									<!-- comment opinion image -->
									<th rowspan="2" style="width: 5%;">
										<div class="align-self-center">
											<c:if test="${comment.opinion eq 'Agree'}">
												<img width="100%" alt="찬성" src="/image/agree.png">
											</c:if>
											<c:if test="${comment.opinion eq 'Disagree'}">
												<img width="100%" alt="반대" src="/image/disagree.png">
											</c:if>
										</div>
									</th>

									<!-- comment writer -->
									<th><div>
											<c:out value="${comment.user.nickname }"></c:out>
										</div></th>

									<!-- comment created date -->
									<th><div class="d-flex" style="float: right; font-size: 7px; color: gray; font-weight: normal;">
											<fmt:formatDate value="${comment.createdAt}" type="both" timeStyle="short" />
										</div></th>
								</tr>
								<tr>
									<!-- commnet content -->
									<td style="width: 85%;">
										<div class="newline">
											<c:out value="${comment.content}"></c:out>
										</div>
									</td>

									<!-- comment button -->
									<td><c:if test="${comment.content ne '(사용자에 의해 삭제된 댓글입니다.)' && comment.content ne '(관리자에 의해 삭제된 댓글입니다.)'}">
											<div style="float: right; padding: 0px;">
												<c:if test="${board.deadline > today && principal ne null && principal ne ''}">
													<button type="button" onClick="modalOpen(${comment.bundle}, ${comment.step });" class="btn badge">답글달기</button>
													<c:if test="${principal.user.username eq comment.user.username }">
														<button type="button" onClick="commentDelete('${board.id}', ${comment.id});" class="badge">삭제</button>
													</c:if>
													<c:if test="${board.user.role ne 'ADMIN' && comment.user.id ne principal.user.id}">
														<button type="button" class="btn badge btn-outline-danger" onclick="replyReportForm('${comment.id}','reply','${comment.content }','${comment.user.nickname}');">신고</button>
													</c:if>
												</c:if>
											</div>
										</c:if></td>
								</tr>
							</table>
						</c:if> <c:if test="${comment.content eq '(사용자에 의해 삭제된 댓글입니다.)' || comment.content eq '(관리자에 의해 삭제된 댓글입니다.)'}">
							<div class="newline">
								<c:out value="${comment.content }"></c:out>
							</div>
						</c:if></li>

					<!-- 답글 -->
					<c:forEach var="reply" items="${board.replys }">
						<c:if test="${reply.step ne 0 }">
							<c:if test="${reply.bundle eq comment.bundle}">
								<ul class="list-group" style="padding-left: 30px;">
									<li class="list-group-item d-flex justify-content-between" style="padding-right: 5px;"><c:if test="${reply.content ne '(사용자에 의해 삭제된 댓글입니다.)' && reply.content ne '(관리자에 의해 삭제된 댓글입니다.)'}">
											<table class="newline">
												<tr class="newline">
													<!-- reply opinion image -->
													<th rowspan="2" style="width: 5%;">
														<div class="align-self-center">
															<c:if test="${reply.opinion eq 'Agree'}">
																<img width="100%" alt="찬성" src="/image/agree.png">
															</c:if>
															<c:if test="${reply.opinion eq 'Disagree'}">
																<img width="100%" alt="반대" src="/image/disagree.png">
															</c:if>
														</div>
													</th>
													<th><div>
															<c:out value="${reply.user.nickname }"></c:out>
														</div></th>
													<th><div class="d-flex" style="float: right; font-size: 7px; color: gray; font-weight: normal;">
															<fmt:formatDate value="${reply.createdAt}" type="both" timeStyle="short" />
														</div></th>
												</tr>
												<tr>
													<!-- reply content -->
													<td>
														<div class="newline">
															<c:out value="${reply.content}"></c:out>
														</div>
													</td>

													<!-- reply button -->
													<td>
														<div style="float: right; padding: 0px;">
															<c:if test="${board.deadline > today && principal ne null && principal ne ''}">
																<%-- 																<button type="button" onClick="modalOpen(${reply.bundle}, ${reply.step });" class="btn badge">답글달기</button> --%>
																<c:if test="${principal.user.username eq reply.user.username }">
																	<button type="button" onClick="commentDelete('${board.id}', ${reply.id});" class="badge">삭제</button>
																</c:if>
																<c:if test="${board.user.role ne 'ADMIN' && reply.user.id ne principal.user.id}">
																	<button type="button" class="btn badge btn-outline-danger" onclick="replyReportForm('${reply.id}','reply','${reply.content }','${reply.user.nickname}');">신고</button>
																</c:if>
															</c:if>
														</div>
													</td>
												</tr>
											</table>
										</c:if> <c:if test="${reply.content eq '(사용자에 의해 삭제된 댓글입니다.)' || reply.content eq '(관리자에 의해 삭제된 댓글입니다.)'}">
											<div class="newline">
												<c:out value="${reply.content }"></c:out>
											</div>
										</c:if></li>
								</ul>
							</c:if>
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>

<!-- modal 영역 -->
<div class="modal-bg" onclick="modalClose();" style="display: none;"></div>
<div class="modal-wrap" style="display: none;">
	<input type="hidden" id="bundle" value="" /> <input type="hidden" id="step" value="" />
	<textarea id="reply-content" style="resize: none;" placeholder="텍스트를 입력하세요."></textarea>
	<div class="d-flex justify-content-between">
		<div class="d-flex justify-content-between reply-opinion" style="width: 80%;">
			<input type="radio" id="reply-agree" name="reply-opinion" value="Agree" /> <label for="reply-agree">찬성</label> <input type="radio" id="reply-disagree" name="reply-opinion" value="Disagree" /> <label
				for="reply-disagree">반대</label>
		</div>
		<div>
			<div id="reply-text-count" class="textCount"></div>
			<button id="reply-save" class="badge">답글달기</button>
		</div>
	</div>
</div>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>