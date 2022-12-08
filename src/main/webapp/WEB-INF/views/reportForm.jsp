<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="https://cdn-icons-png.flaticon.com/512/1157/1157136.png?w=740&t=st=1669096102~exp=1669096702~hmac=bee74bacffb103e93edcdd5b0b9e4b311c8920c56549f54826f16c54be3335fd" rel="shortcut icon"
	type="image/x-icon">
<title>MEET</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/main.css" />
<link rel="stylesheet" href="/css/admin.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.0/font/bootstrap-icons.css">
<link rel="stylesheet" href="https://cdn.rawgit.com/theus/chart.css/v1.0.0/dist/chart.css" />
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css'>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
<script src="/js/admin.js"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>

</head>
<body oncontextmenu="return false">
	<br />
	<div class="container">
		<input type="hidden" id="target_id" value="${target_id }"> <input type="hidden" id="target_type" value="${target_type }">

		<c:if test="${target_type eq 'board' }">
			TITLE
			<div class="form-group">${board_title }</div>
			<hr>
			
			WRITER
			<div class="form-group">${user_nickname }</div>
			<hr>
		</c:if>

		<c:if test="${target_type eq 'user' }">
			NICKNAME
			<div class="form-group">${user_nickname }</div>
			<hr>
		</c:if>

		<c:if test="${target_type eq 'reply' }">
			내용
			<div class="form-group">${board_title }</div>
			<hr>
			
			NICKNAME
			<div class="form-group">${user_nickname }</div>
			<hr>
		</c:if>

		<div class="form-group">
			신고항목<br /> <select id="report_code">
				<option value="1">광고성</option>
				<option value="2">도배성</option>
				<option value="3">정치성</option>
				<option value="4">욕설, 비난성</option>
				<option value="5">선정성</option>
				<option value="6">폭력성</option>
				<option value="7">혐오 발언 또는 상징</option>
				<option value="8">따돌림 또는 괴롭힘</option>
				<option value="9">스팸</option>
				<option value="10">지식재산권 침해</option>
				<option value="11">불법 또는 규제 상품 판매</option>
				<option value="12">자살 또는 자해</option>
				<option value="13">사행성</option>
				<option value="14">기타</option>
			</select>
		</div>
		<hr>
		<div class="form-group">
			신고내용<br />
			<textarea class="form-control" rows="2" id="content"></textarea>
		</div>
		<button type="button" id="report-post" class="badge">report</button>
	</div>
</body>
<script src="/js/report.js"></script>
</html>