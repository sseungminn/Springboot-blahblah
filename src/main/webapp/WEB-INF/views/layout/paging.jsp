<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 페이징처리 시작-->
<c:if test="${lastlistpage > 1}">
	<ul class="pagination justify-content-center">
		<!-- 이전 버튼 -->
		<li class="page-item ${boards.first  ? 'disabled' : ''}"><a class="page-link" href="?page=${boards.number-1 }&search=${param.search}&type=${type}">&lt;</a></li>

		<!-- 가운데 숫자 -->
		<c:forEach var="i" begin="${firstlistpage}" end="${lastlistpage}">
			<li class="page-item ${boards.number eq i-1 ? 'disabled' : ''}"><a class="page-link" href="?page=${i-1}&search=${param.search}&type=${type}">${i }</a></li>
		</c:forEach>

		<!-- 다음 버튼 -->
		<li class="page-item ${boards.last  ? 'disabled' : ''}"><a class="page-link" href="?page=${boards.number+1 }&search=${param.search}&type=${type}">&gt;</a></li>
	</ul>
</c:if>
<!-- 페이징처리 끝-->
