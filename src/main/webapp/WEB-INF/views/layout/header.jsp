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
<title>주절주절</title>
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
<script>
function noEvent() {    
	if (event.keyCode == 116 || event.keyCode == 123) {        
		event.keyCode= 2;        
		return false;    
	} else if(event.ctrlKey && (event.keyCode==78 || event.keyCode == 82)) {        
		return false;
	}
}
document.onkeydown = noEvent;
</script>
</head>
<body oncontextmenu="return false" class="d-flex flex-column min-vh-100" style="position: sticky;">
	<nav class="navbar navbar-expand-md navbar-light" style="background-color: #AAB9FF;">
		<a class="navbar-brand" href="/"><img alt="logo" width="30" height="30" src="/image/blahblah_logo.png"></a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">
			<ul class="navbar-nav">
				<!-- 로그인 안 된 상태 -->
				<c:if test="${principal eq null}">
					<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인 및 회원가입</a></li>
				</c:if>
				<!-- 로그인 된 상태 -->
				<c:if test="${principal ne null}">
					<c:if test="${principal.user.role eq 'ROLE_ADMIN'}">
						<li class="nav-item"><a class="nav-link" href="/admin">관리자페이지</a></li>
						<li class="nav-item"><a class="nav-link" onclick="window.open('/room','chat','width=550, height=850, menubar=no, status=no, toolbar=no');" href="#">채팅방(임시)</a></li>
						<li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a></li>
						<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
						<li class="nav-item"><a class="nav-link" href="/user/updateForm">내 정보</a></li>
					</c:if>
					<c:if test="${principal.user.role eq 'ROLE_USER'}">
						<li class="nav-item"><a class="nav-link" href="/room">채팅방(임시)</a></li>
						<li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a></li>
						<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
						<li class="nav-item"><a class="nav-link" href="/user/updateForm">내 정보</a></li>
					</c:if>
					<c:if test="${principal.user.role eq 'ROLE_BANNED'}">
						<script>
  							location.href='/error';
 						</script>
					</c:if>
				</c:if>
			</ul>
		</div>
	</nav>