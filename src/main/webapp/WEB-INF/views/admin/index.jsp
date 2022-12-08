<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<script>
function updateFormOpen(e,target_type){
	if('${type.substring(6)}' == 'report' || '${type.substring(6)}' == 'processed'){
		window.open('/admin/'+target_type+'/updateForm/'+e,'admin-'+target_type+'-update-form','width=700, height=700, menubar=no, status=no, toolbar=no');
	}else{
		window.open('/admin/${type.substring(6)}/updateForm/'+e,'admin-${type.substring(6)}-update-form','width=700, height=700, menubar=no, status=no, toolbar=no');
	}
}
$(document).ready(function() {
	if("${type}" != 'admin_report' && "${type}" != 'admin_processed'){
		$('.snd_menu').hide();
	}
	// 메뉴 슬라이드
	$('#index_menu > li > a').click(function() {
		$(this).siblings($('.snd_menu')).slideToggle('fast');
	})

	// 버튼 클릭 시 스타일 변경
	$('li > a').focus(function() {
		$(this).addClass('selec');
	})
	$("li > a").blur(function() {
		$(this).removeClass('selec');
	})

});
</script>
<div class="container-fluid">
	<div class="row flex-nowrap">
		<!-- left nav bar start -->
		<div class="col-2 bd-sidebar">
			<br />
			<div class="container search-bar input-group mb-3">
				<form>
					<c:if test="${type eq 'admin_user'}">
						<input data-addui='input' name="search" value="${param.search}" type='text' placeholder="ID or Email 검색" />
						<br />
					</c:if>
					<c:if test="${type eq 'admin_board'}">
						<input data-addui='input' name="search" value="${param.search}" type='text' placeholder="Title 검색" />
						<br />
					</c:if>
					<c:if test="${type eq 'admin_reply'}">
						<input data-addui='input' name="search" value="${param.search}" type='text' placeholder="User_id 검색" />
						<br />
					</c:if>
					<c:if test="${type eq 'admin_report' || type eq 'admin_processed'}">
						<div class="form-group">
							<select name="search" onchange="location.href='/${type}?search='+this.value">
								<option value="">NO SELECT</option>
								<option value="user" ${param.search eq 'user' ? 'selected' : ''}>user</option>
								<option value="board" ${param.search eq 'board' ? 'selected' : ''}>board</option>
								<option value="reply" ${param.search eq 'reply' ? 'selected' : ''}>reply</option>
							</select>
						</div>
					</c:if>
					<div>
						<ul id="index_menu" class="admin-ul">
							<li class="admin-li"><a href="/admin_user"> 유저관리</a></li>
							<li class="admin-li"><a href="/admin_board"> 게시글관리</a></li>
							<li class="admin-li"><a href="/admin_reply"> 댓글관리</a></li>
							<li class="admin-li"><a href="/admin_report">&gt;&nbsp;신고관리</a>
								<ul class="admin-ul snd_menu sub_menu">
									<li class="admin-li"><div>
											<a style="text-decoration: none;" href="/admin_report">처리중</a>
										</div></li>
								</ul>
								<ul class="admin-ul snd_menu sub_menu">
									<li class="admin-li"><div>
											<a style="text-decoration: none;" href="/admin_processed">처리됨</a>
										</div></li>
								</ul></li>
						</ul>
					</div>
				</form>
			</div>
		</div>
		<!-- left nav bar end -->

		<!-- main start -->
		<div>
			<table class="table" style="table-layout: fixed; padding: 0; margin: 0; text-align: center;">
				<thead style="background-color: #eee;">
					<tr height="20px">
						<c:if test="${type eq 'admin_user' }">
							<th width="24%">id</th>
							<th width="16%">nickname</th>
							<th width="18%">email</th>
							<th width="8%">gender</th>
							<th width="8%">role</th>
							<th width="10%">createdAt</th>
							<th width="8%">update</th>
							<th width="8%">delete</th>
						</c:if>
						<c:if test="${type eq 'admin_board' }">
							<th width="24%">id</th>
							<th width="16%">title</th>
							<th width="18%">user_id</th>
							<th width="10%">createdAt</th>
							<th width="8%">update</th>
							<th width="8%">delete</th>
						</c:if>
						<c:if test="${type eq 'admin_reply' }">
							<th width="3%">id</th>
							<th width="16%">board_id</th>
							<th width="4%">bundle</th>
							<th width="4%">step</th>
							<th width="24%">content</th>
							<th width="16%">user_id</th>
							<th width="7%">createdAt</th>
							<th width="6%">update</th>
							<th width="6%">delete</th>
						</c:if>
						<c:if test="${type eq 'admin_report' || type eq 'admin_processed'}">
							<th width="3%">id</th>
							<th width="8%">target_type</th>
							<th width="8%">report_code</th>
							<th width="16%">target_id</th>
							<th width="16%">status</th>
							<th width="7%">createdAt</th>
							<th width="6%">receive</th>
							<th width="6%">delete</th>
						</c:if>
					</tr>
				</thead>

				<tbody>
					<c:if test="${type eq 'admin_user' }">
						<c:forEach items="${list.content }" var="user">
							<tr style="padding: 0; margin: 0;">
								<td id="id" style="padding: 0; margin: 0;">${user.id}</td>
								<td style="padding: 0; margin: 0;">${user.nickname }</td>
								<td style="padding: 0; margin: 0;">${user.email }</td>
								<td style="padding: 0; margin: 0;">${user.gender }</td>
								<td style="padding: 0; margin: 0;">${user.role }</td>
								<td style="padding: 0; margin: 0;">${user.createdAt }</td>
								<c:if test="${user.id eq principal.user.id }">
									<td style="padding: 0; margin: 0;"><button type="button" onclick="location.href='/user/updateForm'" class="btn btn-outline-primary">수정</button></td>
								</c:if>
								<c:if test="${user.id ne principal.user.id }">
									<td style="padding: 0; margin: 0;"><button type="button" onclick="updateFormOpen('${user.id}');" class="btn btn-outline-primary">수정</button></td>
								</c:if>
								<td style="padding: 0; margin: 0;"><button type="button" onclick="index.delete('${type.substring(6)}','${user.id}',0);" class="btn btn-outline-danger">삭제</button></td>
								<!-- 		    		<td style="padding: 0; margin: 0;"> -->
								<!-- 			    		<button type="button" class="btn btn-outline-primary badge">수정</button><br/> -->
								<!-- 			    		<button type="button" class="btn btn-outline-danger badge">삭제</button> -->
								<!-- 		    		</td> -->
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${type eq 'admin_board' }">
						<c:forEach items="${list.content }" var="board">
							<tr style="padding: 0; margin: 0;">
								<td id="id" style="padding: 0; margin: 0;">${board.id}</td>
								<td style="padding: 0; margin: 0;">${board.title }</td>
								<td style="padding: 0; margin: 0;">${board.user.id }</td>
								<td style="padding: 0; margin: 0;">${board.createdAt }</td>
								<td style="padding: 0; margin: 0;"><button type="button" onclick="updateFormOpen('${board.id}');" class="btn btn-outline-primary">수정</button></td>
								<td style="padding: 0; margin: 0;"><button type="button" onclick="index.delete('${type.substring(6)}','${board.id}',0)" class="btn btn-outline-danger">삭제</button></td>
							</tr>
						</c:forEach>
					</c:if>

					<c:if test="${type eq 'admin_reply' }">
						<c:forEach items="${list.content }" var="reply">
							<tr style="padding: 0; margin: 0;">
								<td id="id" style="padding: 0; margin: 0;">${reply.id}</td>
								<td id="board_id" style="padding: 0; margin: 0;">${reply.board.id }</td>
								<td style="padding: 0; margin: 0;">${reply.bundle }</td>
								<td style="padding: 0; margin: 0;">${reply.step}</td>
								<td style="padding: 0; margin: 0;">${reply.content}</td>
								<td style="padding: 0; margin: 0;">${reply.user.id}</td>
								<td style="padding: 0; margin: 0;">${reply.createdAt}</td>
								<td style="padding: 0; margin: 0;"><button type="button" onclick="updateFormOpen(${reply.id});" class="btn btn-outline-primary">수정</button></td>
								<td style="padding: 0; margin: 0;"><button ${(reply.opinion eq null || reply.opinion eq '') ? 'disabled' : ''} type="button"
										onclick="index.delete('${type.substring(6)}','${reply.board.id}',${reply.id })" class="btn btn-outline-danger">삭제</button></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${type eq 'admin_report' }">
						<c:forEach items="${list.content }" var="report">
							<c:if test="${report.status eq '처리중'}">
								<tr style="padding: 0; margin: 0;">
									<td id="id" style="padding: 0; margin: 0;">${report.id }</td>
									<td id="target_type" style="padding: 0; margin: 0;">${report.targetType}</td>
									<td style="padding: 0; margin: 0;"><c:choose>
											<c:when test="${report.reportCode eq '1'}">광고성</c:when>
											<c:when test="${report.reportCode eq '2'}">도배성</c:when>
											<c:when test="${report.reportCode eq '3'}">정치성</c:when>
											<c:when test="${report.reportCode eq '4'}">욕설, 비난성</c:when>
											<c:when test="${report.reportCode eq '5'}">선정성</c:when>
											<c:when test="${report.reportCode eq '6'}">폭력성</c:when>
											<c:when test="${report.reportCode eq '7'}">혐오 발언 또는 상징</c:when>
											<c:when test="${report.reportCode eq '8'}">따돌림 또는 괴롭힘</c:when>
											<c:when test="${report.reportCode eq '9'}">스팸</c:when>
											<c:when test="${report.reportCode eq '10'}">지식재산권 침해</c:when>
											<c:when test="${report.reportCode eq '11'}">불법 또는 규제 상품 판매</c:when>
											<c:when test="${report.reportCode eq '12'}">자살 또는 자해</c:when>
											<c:when test="${report.reportCode eq '13'}">사행성</c:when>
											<c:when test="${report.reportCode eq '14'}">기타</c:when>
										</c:choose></td>
									<td style="padding: 0; margin: 0;">
										<button type="button" class="btn btn-outline-primary badge" onclick="updateFormOpen('${report.targetId}','${report.targetType }');">${report.targetId }</button>
									</td>
									<td style="padding: 0; margin: 0;">${report.status}</td>
									<td style="padding: 0; margin: 0;">${report.createdAt}</td>
									<td style="padding: 0; margin: 0;"><button type="button" onclick="updateFormOpen(${report.id},'report');" class="btn btn-outline-primary">접수</button></td>
									<td style="padding: 0; margin: 0;"><button type="button" onclick="report.reportDelete(${report.id});" class="btn btn-outline-danger">삭제</button></td>
								</tr>
							</c:if>
						</c:forEach>
					</c:if>

					<c:if test="${type eq 'admin_processed' }">
						<c:forEach items="${list.content }" var="report">
							<c:if test="${report.status eq '처리됨'}">
								<tr style="padding: 0; margin: 0;">
									<td id="id" style="padding: 0; margin: 0;">${report.id }</td>
									<td id="target_type" style="padding: 0; margin: 0;">${report.targetType}</td>
									<td style="padding: 0; margin: 0;"><c:choose>
											<c:when test="${report.reportCode eq '1'}">광고성</c:when>
											<c:when test="${report.reportCode eq '2'}">도배성</c:when>
											<c:when test="${report.reportCode eq '3'}">정치성</c:when>
											<c:when test="${report.reportCode eq '4'}">욕설, 비난성</c:when>
											<c:when test="${report.reportCode eq '5'}">선정성</c:when>
											<c:when test="${report.reportCode eq '6'}">폭력성</c:when>
											<c:when test="${report.reportCode eq '7'}">혐오 발언 또는 상징</c:when>
											<c:when test="${report.reportCode eq '8'}">따돌림 또는 괴롭힘</c:when>
											<c:when test="${report.reportCode eq '9'}">스팸</c:when>
											<c:when test="${report.reportCode eq '10'}">지식재산권 침해</c:when>
											<c:when test="${report.reportCode eq '11'}">불법 또는 규제 상품 판매</c:when>
											<c:when test="${report.reportCode eq '12'}">자살 또는 자해</c:when>
											<c:when test="${report.reportCode eq '13'}">사행성</c:when>
											<c:when test="${report.reportCode eq '14'}">기타</c:when>
										</c:choose></td>
									<td style="padding: 0; margin: 0;">
										<button type="button" class="btn btn-outline-primary badge" onclick="updateFormOpen('${report.targetId}','${report.targetType }');">${report.targetId }</button>
									</td>
									<td style="padding: 0; margin: 0;">${report.status}</td>
									<td style="padding: 0; margin: 0;">${report.createdAt}</td>
									<td style="padding: 0; margin: 0;"><button type="button" disabled onclick="updateFormOpen(${report.id},'report');" class="btn btn-outline-primary">접수</button></td>
									<td style="padding: 0; margin: 0;"><button type="button" onclick="report.reportDelete(${report.id});" class="btn btn-outline-danger">삭제</button></td>
								</tr>
							</c:if>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<%@ include file="paging.jsp"%>
		</div>
		<!-- main end -->
	</div>
</div>
<link href="/css/addInput.css" rel="stylesheet">
<script src="/js/addInput.js"></script>
<script src="/js/admin.js"></script>
<script src="/js/report.js"></script>
<%@ include file="../layout/footer.jsp"%>