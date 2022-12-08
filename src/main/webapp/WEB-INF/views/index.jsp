<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp"%>
<br />
<div class="container search-bar input-group mb-3">
	<form class="form-inline">
		<input data-addui='input' name="search" value="${param.search}" type='text' placeholder="Type title to search" /><br />
		<%-- 	  <input type="search" name="search"  value="${param.search}" class="form-control rounded-pill" placeholder="Type title to search" autofocus>&nbsp; --%>
		<input type="hidden" name="today" id="today" value="${today }">
		<div class="input-group-append">
			<button class="btn btn-outline-secondary rounded-pill">Search</button>
		</div>
	</form>
</div>
<br />
<div class="container show-grid">
	<!-- 투표중, 마감됨 버튼 시작 -->
	<div class="row">
		<div class="col-md-6">
			<div class="center">
				<a href="/voting"> <span data-attr="vot"></span> <span data-attr="ing"></span>
				</a>
			</div>
		</div>

		<div class="col-md-6">
			<div class="center">
				<a href="/voted"> <span data-attr="vot"></span> <span data-attr="ed"></span>
				</a>
			</div>
		</div>
	</div>
	<!-- 투표중, 마감됨 버튼 끝 -->

	<!-- 토론방 리스트 시작 -->
	<div class="row">
		<div class="col-md-12">

			<c:if test="${boards.content eq null || boards.content eq '' || boards.content eq '[]' }">
				<div class="justify-content-center">
					<a style="width: 100%;" href="/board/saveForm" class="btn btn-outline-primary"> <!-- 						<svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-plus-square" viewBox="0 0 16 16"> -->
						<!-- 						  <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/> --> <!-- 						  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/> -->
						<!-- 						</svg><br/> --> <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16">
						  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z" />
						  <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
						</svg><br />
						<h1></h1> <font class="h4 font-weight-bold">토론 생성하기</font>
					</a>
					<div style="text-align: center;">
						<h1></h1>
						<b>${type eq 'voting' ? '진행중인 토론이 없습니다.' : '종료된 토론이 없습니다.' }</b>
					</div>
					<!-- 					<i class="bi-file-plus"></i> -->
					<!-- 					<i class="bi-plus-circle"></i> -->
					<!-- 					<i class="bi-plus-square"></i> -->
				</div>
			</c:if>
			<c:forEach var="board" items="${boards.content}">
				<c:url value="/auth/board/${board.id }" var="detail">
					<c:param name="page" value="${boards.number }" />
					<c:param name="search" value="${param.search}" />
					<c:param name="type" value="${type }" />
				</c:url>
				<div class="justify-content-center">
					<a style="width: 100%;" href="${detail }" class="btn btn-outline-primary"> <q class="h1"><c:out value="${board.title }"></c:out></q><br /> 투표마감 : <%-- 						${board.deadline.toString().substring(0,10)}<br/> --%>
						<fmt:parseDate value="${board.deadline}" var="deadlineDate" pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:parseDate value="${today}" var="todayDate" pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:parseNumber
							value="${todayDate.time / (1000*60*60*24)}" integerOnly="true" var="todayNum" /> <fmt:parseNumber value="${deadlineDate.time / (1000*60*60*24)}" integerOnly="true" var="deadlineNum" /> <fmt:parseNumber
							value="${Math.abs((deadlineDate.time - todayDate.time)/(1000*60))}" integerOnly="true" var="minutes" /> ${Math.abs(deadlineNum - todayNum) ne 0 ? Math.abs(deadlineNum - todayNum) : minutes}
						${Math.abs(deadlineNum - todayNum) ne 0 ? '일' : '분'} ${(deadlineDate.time - todayDate.time)/(1000*60) > 0 ? '남음' : '지남' } <!-- 						<br/> --> <%-- 						<c:set var="vote_cnt" value="0"/> --%> <%-- 						<c:forEach var="reply" items="${board.replys}" varStatus="status"> --%>
						<%-- 						<c:if test="${reply.opinion ne '' }"> --%> <%-- 							<c:set var="vote_cnt" value="${vote_cnt+1 }"/> --%> <%-- 							<c:if test="${status.last }"> --%> <%-- 								<div>의견수 : ${vote_cnt}</div> --%>
						<%-- 							</c:if> --%> <%-- 						</c:if> --%> <%-- 						</c:forEach> --%>
					</a>
				</div>
				<h1></h1>
			</c:forEach>
		</div>
	</div>
	<!-- 토론방 리스트 끝 -->
	<!-- 페이징 시작 -->
	<c:if test="${boards.content ne null && boards.content ne '' && boards.content ne '[]' }">
		<%@ include file="layout/paging.jsp"%>
	</c:if>
	<!-- 페이징 끝 -->
</div>
<link href="/css/addInput.css" rel="stylesheet">
<script src="/js/addInput.js"></script>
<%@ include file="layout/footer.jsp"%>