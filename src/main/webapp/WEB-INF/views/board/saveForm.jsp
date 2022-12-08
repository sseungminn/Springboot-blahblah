<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<script>
if("${principal.user.nickname}" == null || "${principal.user.nickname}" == ''){
	nicknameCheck = confirm('닉네임 등록시 이용가능합니다. 등록하시겠습니까?');
	if(nicknameCheck == true){
		location.href="/user/updateForm";
	}else{
		location.href="/";
	}
}
</script>
<br />
<div class="container">
	<form>
		<input type="hidden" id="user_id" value="${principal.user.id }">
		<div class="form-group">
			<input data-addui='input' id="title" type='text' autofocus placeholder="주제" /><br />
			<!-- 			<input type="text" id="title"> -->
		</div>

		<div class="form-group">
			<label for="content" style="font-size: 80%; color: #8e8e8e;">주제 설명 및 자료</label>
			<textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>

		<div class="form-group">
			<font color="#8e8e8e" size="2">투표마감일</font><br /> <input type="date" id="deadline">
		</div>
	</form>
	<button id="board-save" class="btn btn-primary">Write</button>
</div>

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
<link href="/css/addInput.css" rel="stylesheet">
<script src="/js/addInput.js"></script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>