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
<body>
	<br />
	<div class="container">
		<c:if test="${target_type eq 'board' }">
			<div id="id" class="form-group">${board.id }</div>
			<hr>
			<div class="form-group">
				TITLE : <input id="title" value="${board.title}" type='text' />
			</div>
			<div class="form-group">
				<label for="content" style="font-size: 80%; color: #8e8e8e;">CONTENT</label>
				<textarea class="form-control summernote" rows="5" id="content">${board.content }</textarea>
			</div>
		</c:if>

		<c:if test="${target_type eq 'user' }">
			<div id="id" class="form-group">${user.id }</div>
			<hr>
			<div id="email" class="form-group">EMAIL : ${user.email }</div>
			<div class="form-group">
				NICKNAME : <input id="nickname" value="${user.nickname }" type='text' />
			</div>
			<div class="form-group">
				ROLE : <select id="role">
					<option value="ROLE_USER" ${user.role eq 'ROLE_USER' ? 'selected' : ''}>USER</option>
					<option value="ROLE_ADMIN" ${user.role eq 'ROLE_ADMIN' ? 'selected' : ''}>ADMIN</option>
					<option value="ROLE_BANNED" ${user.role eq 'ROLE_BANNED' ? 'selected' : ''}>BANNED</option>
				</select>
			</div>
		</c:if>

		<c:if test="${target_type eq 'reply' }">
			<div class="form-group">
				ID : <input id="id" value="${reply.id}" type='text' readonly />
			</div>
			<div class="form-group">
				BOARD_ID : <input id="board_id" value="${reply.board.id}" type='text' readonly />
			</div>
			<div class="form-group">
				BOARD_TITLE : <input value="${reply.board.title}" type='text' readonly />
			</div>
			<div class="form-group">
				REPLY_CONTENT :
				<textarea id="content" class="form-control" rows="1">${reply.content }</textarea>
			</div>
			<div class="form-group">
				USER_ID : <input id="user_id" value="${reply.user.id}" type='text' readonly />
			</div>
		</c:if>
		<c:if test="${target_type eq 'report' }">
			<div class="form-group">
				ID : <input id="id" value="${report.id}" type='text' readonly />
			</div>
			<div class="form-group">
				TARGET_TYPE : <input id="target_type" value="${report.targetType}" type='text' readonly />
			</div>
			<div class="form-group">
				REPORT_CODE : <input value="${report.reportCode}" type='text' readonly />
			</div>
			<div class="form-group">
				TARGET_ID : <input id="target_id" type="text" value="${report.targetId }" readonly />
			</div>
			<div class="form-group">
				CONTENT : <input id="status" value="${report.content}" type='text' readonly />
			</div>
		</c:if>
		<button type="button" id="admin-${target_type}-update" class="badge">${target_type eq 'report' ? 'REPORT' : 'UPDATE'}</button>
	</div>
</body>
<script>
$('.summernote').summernote({
	placeholder: '',
	height: 300,
	toolbar: [
// 	   [groupName, [list of button]]
	   ['fontname', ['fontname']],
	   ['fontsize', ['fontsize']],
	   ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
	   ['color', ['forecolor','color']],
	   ['table', ['table']],
	   ['para', ['ul', 'ol', 'paragraph']],
	   ['height', ['height']],
	   ['insert',['picture','link','video']],
	   ['view', ['fullscreen', 'help']]
	]
});
</script>
<script src="/js/admin.js"></script>
</html>