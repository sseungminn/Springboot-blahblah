<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<c:set var="user" value="${principal.user}" />
<br />
<div class="container" id="wrapper">
	<div class="d-flex justify-content-center">
		<div>
			<input type="hidden" id="id" value="${user.id}" /><br /> <input data-addui='input' id="nickname" value='${user.nickname}' type='text' autofocus placeholder="Nickname" /><br />
			<%-- 			<input type="text" id="nickname2" value="${user.nickname}" autocomplete="off" autofocus placeholder="2~8글자 "/><br/> --%>
			<div id="nicknameError"></div>
			연령대<br />
			<div>
				<select id="age_range">
					<option value="" ${(user.age_range eq '') || (user.age_range eq null) ? 'selected' : ''}>선택 안 함</option>
					<option value="~9" ${user.age_range eq '~9' ? 'selected' : ''}>10세미만</option>
					<option value="10~19" ${user.age_range eq '10~19' ? 'selected' : ''}>10대</option>
					<option value="20~29" ${user.age_range eq '20~29' ? 'selected' : ''}>20대</option>
					<option value="30~39" ${user.age_range eq '30~39' ? 'selected' : ''}>30대</option>
					<option value="40~49" ${user.age_range eq '40~49' ? 'selected' : ''}>40대</option>
					<option value="50~59" ${user.age_range eq '50~59' ? 'selected' : ''}>50대</option>
					<option value="60~69" ${user.age_range eq '60~69' ? 'selected' : ''}>60대</option>
					<option value="70~79" ${user.age_range eq '70~79' ? 'selected' : ''}>70대</option>
					<option value="80~" ${user.age_range eq '80~' ? 'selected' : ''}>80세이상</option>
				</select>
			</div>
			<br /> 이메일<br />
			<div id="email">${user.email}</div>
			<br /> 성별<br />
			<div>
				<select id="gender">
					<option value="x" ${(user.gender eq '') || (user.gender eq null) ? 'selected' : ''}>선택 안 함</option>
					<option value="male" ${user.gender eq 'male' ? 'selected' : ''}>남</option>
					<option value="female" ${user.age_range eq 'female' ? 'selected' : ''}>여</option>
				</select>
			</div>
			<br />
			<c:if test="${user.role eq 'ROLE_ADMIN' }">
				ROLE<br />
				<div>
					<select id="role">
						<option value="ROLE_ADMIN" ${user.role eq 'ROLE_ADMIN' ? 'selected' : ''}>ADMIN</option>
						<option value="ROLE_USER" ${user.role eq 'ROLE_USER' ? 'selected' : ''}>USER</option>
						<option value="ROLE_BANNED" ${user.role eq 'ROLE_BANNED' ? 'selected' : ''}>BANNED</option>
					</select>
				</div>
				<br />
			</c:if>

			<button type="button" id="user-update" class="btn btn-primary">Update</button>
			<button type="button" onclick="history.back();" class="btn btn-primary">Cancel</button>
			<hr>
			<font size="6">내가 쓴 글</font><br />
			<c:forEach items="${myBoard}" var="board" varStatus="status">
				<a href="/auth/board/${board.id}">"${board.title }"</a>
				<br />
			</c:forEach>
		</div>
	</div>
</div>
<link href="/css/addInput.css" rel="stylesheet">
<script src="/js/user.js"></script>
<script src="/js/addInput.js"></script>
<%@ include file="../layout/footer.jsp"%>
