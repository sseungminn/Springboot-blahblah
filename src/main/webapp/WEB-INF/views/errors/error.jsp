<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/css/bootstrap.min.css'>
<title>ERROR PAGE</title>
</head>
<body class="container" style="display: inline-block;">
	<div class="errorPage" style="margin-left: 10vw;">
		<br />
		<br />
		<br />
		<br />
		<br />
		<br /> <span class="errorHead">Error!!</span>
		<h2>
			<b> <c:choose>
					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 400}">
        		400 Bad Request<br />잘못된 요청입니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 401}">
        		401 Unauthorized<br />인증이 되지 않았습니다.
        	</c:when>

					<c:when test="${code eq 403}">
        		403 Forbidden<br />권한이 없습니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 404}">
        		404 Not Found<br />요청받은 리소스를 찾을 수 없습니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 405}">
        		405 Method Not Allowed<br />요청한 메소드는 사용할 수 없습니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 409}">
        		409 Conflict<br />다른 요청이나 서버의 구성과 충돌이 발생했습니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 429}">
        		Too Many Requests<br />지정된 시간에 너무 많은 요청을 받았습니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 500}">
        		500 Internal Server Error<br />예측하지 못한 에러가 발생했습니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 502}">
        		502 Bad Gateway<br />유효하지 않은 응답입니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 503}">
        		503 Service Unavailable<br />서버가 요청을 처리할 준비가 되지 않았습니다.
        	</c:when>

					<c:when test="${requestScope['javax.servlet.error.status_code'] eq 504}">
        		504 Gateway Timeout<br />게이트웨이 시간 초과
        	</c:when>
					<c:otherwise>
        		이용이 정지된 계정입니다.
        	</c:otherwise>
				</c:choose>
			</b>
		</h2>
		<c:if test="${requestScope['javax.servlet.error.status_code'] eq '' || requestScope['javax.servlet.error.status_code'] eq null}">
        	이전으로 돌아가려면 <a href="/logout">여기</a>를 클릭하세요.
        </c:if>
		<c:if test="${requestScope['javax.servlet.error.status_code'] ne '' && requestScope['javax.servlet.error.status_code'] ne null}">
        	이전으로 돌아가려면 <a onclick="history.back(1);" href="#">여기</a>를 클릭하세요.
        </c:if>
	</div>
	<div style="float: right; padding-right: 5%;">
		<img alt="error_img" src="/image/error_img.png" style="width: 18vw; margin-top: -15vh;">
	</div>
</body>
</html>