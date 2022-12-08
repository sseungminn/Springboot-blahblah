<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<c:if test="${not empty principal }">
	<script>
		location.href = "<c:url value='/'/>";
	</script>
</c:if>
<br />
<div class="container">
	<form>
		<input type="hidden" name="username"> <input type="hidden" name="password">

		<div class="text-center">
			<!-- 카카오 로그인(카카오정보로 블로그 회원가입)-->
			<a href="https://kauth.kakao.com/oauth/authorize?client_id=2762ad5ea7e443f3f9716744680f2689&redirect_uri=http://ec2-15-165-250-155.ap-northeast-2.compute.amazonaws.com//auth/kakao/callback&response_type=code"> <img height="40px"
				src="/image/kakao_login_medium_narrow.png" />
			</a> <br />
			<h1></h1>

			<!-- 구글 로그인(구글정보로 블로그 회원가입) -->
			<a href="/oauth2/authorization/google"> <img height="40px" src="/image/btn_google_signin_light_normal_web.png" />
			</a><br />
			<h1></h1>

			<!-- 페북 로그인(페북정보로 블로그 회원가입) -->
			<!-- AWS HTTPS 설정 안 했음 페북은 나중에? -->
			<!-- 			<a href="/oauth2/authorization/facebook"> -->
			<!-- 				<img height="37px"  width="164px" src="/image/facebook_btn.png" /> -->
			<!-- 			</a><br /> -->
			<!-- 			<h1></h1> -->

			<!--  네이버 로그인(네이버정보로 블로그 회원가입) -->
			<a href="/oauth2/authorization/naver"> <img height="38px" width="164px" src="/image/naver_btn.png" />
			</a><br />
			<h1></h1>

		</div>
	</form>

</div>

<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>

