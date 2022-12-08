<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<c:set var="user" value="${principal.user}" />
<br />
<div class="container" id="wrapper">
	<div class="d-flex justify-content-center">
		<div>

			Nickname<br />
			<div class="h5 font-weight-bold">${profile.nickname }</div>
			<br /> 연령대<br />
			<div class="h5 font-weight-bold">${profile.age_range ne '' ? profile.age_range : '-'}</div>
			<br /> 이메일<br />
			<div class="h5 font-weight-bold">${profile.email}</div>
			<br />

			<c:if test="${profile.gender ne 'x'  && profile.gender ne null && profile.gender ne ''}">
				성별<br />
				<div class="h5 font-weight-bold">${profile.gender eq 'M' || profile.gender eq 'male' ? '남성' : '여성'}</div>
				<br />
			</c:if>

			<c:if test="${profile.id eq principal.user.id }">
				<a class="btn badge btn-outline-dark" href="/user/updateForm">Update</a>
				<br />
			</c:if>
		</div>
	</div>
	<hr>
	<div class="justify-content-center" style="text-align: center;">
		<font size="6">${profile.nickname }님이 올린 토론</font><br />
		<c:forEach items="${boards}" var="board" varStatus="status">
			<h3>
				<a href="/auth/board/${board.id}">"${board.title }" - ${board.deadline > today ? '투표중' : '마감됨'}</a>
			</h3>
			<br />
		</c:forEach>
	</div>
</div>
<link href="/css/addInput.css" rel="stylesheet">
<script src="/js/user.js"></script>
<script src="/js/addInput.js"></script>
<%@ include file="../layout/footer.jsp"%>
