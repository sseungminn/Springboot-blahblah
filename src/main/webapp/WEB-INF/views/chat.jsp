<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.js"></script>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/chat.css" />
<script src="/js/chat.js"></script>
</head>

<body oncontextmenu="return false">
	<div id="container" class="container">
		<div class="now"></div>
		<div style="display: inline-block; width: 100%;">
			<h1>"${roomName}" 채팅방</h1>
			<button type="button" onclick="history.back();" style="float: right; margin-bottom: 3px;">나가기</button>
		</div>
		<input type="hidden" id="sessionId" value=""> <input type="hidden" id="roomNumber" value="${roomNumber}">

		<div id="chating" class="chating"></div>

		<div id="yourName">
			<table class="inputTable">
				<tr>
					<th>사용자명</th>
					<th><input type="text" name="userName" id="userName" value="${principal.user.nickname }" readonly></th>
					<th><button onclick="chatName()" id="startBtn">이름 등록</button></th>
				</tr>
			</table>
		</div>
		<div id="yourMsg">
			<table class="inputTable">
				<tr>
					<th>채팅</th>
					<th><textarea id="chatting" rows="7" cols="15" placeholder="보내실 메시지를 입력하세요." wrap="hard"></textarea></th>
				</tr>
				<!-- 				<tr> -->
				<!-- 					<th>파일업로드</th> -->
				<!-- 					<th><input type="file" id="fileUpload" accept="image/gif, image/jpeg, image/png, image/jpg"></th> -->
				<!-- 					<th><button onclick="fileSend()" id="sendFileBtn">파일올리기</button></th> -->
				<!-- 				</tr> -->
			</table>
		</div>
		<div style="font-size: 10pt; color: red;">* 새로고침시 대화내용이 사라질 수 있습니다.</div>
		<div style="font-size: 10pt; color: red;">* 매너 채팅해주세요.</div>
	</div>
</body>
</html>