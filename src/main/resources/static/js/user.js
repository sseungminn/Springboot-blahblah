var index = {
	init: function(){
		$("#user-update").on("click", ()=>{ 
			this.update();
		});
//		//닉네임이 비었으면 업데이트 버튼 비활성화
//		$("#nickname").on('propertychange change keyup paste input', function() {
//		if ($("#nickname").val() == '') {
//			$("#user-update").attr("disabled", true);
//		} else {
//			if($("#nicknameError").css("color")=="rgb(0, 128, 0)" || $("#nicknameError").val()=="" || $("#nicknameError").val()== null){ // 초록색이면
//				$("#user-update").attr("disabled", false);
//			} else {
//				$("#user-update").attr("disabled", true);
//			}
//		}
//		});
		
//		// 회원수정 닉네임 양식 체크
//		$("#nickname").on('propertychange change keyup paste input', function() {
//			let nicknameValue = $("#nickname").val();
//			let fontColor;
//			let comment;
//			// 영문, 한글, 숫자만 가능 But 2~8글자
//			var pattern = /^([0-9]|[a-z]|[A-Z]|[가-힣]){2,8}$/;
//			if(!pattern.test(nicknameValue)){
//				fontColor = "red";
//				comment = "2~8글자의 영문,한글,숫자만 사용가능합니다.";
//				$("#user-update").attr("disabled", true);
//			} else {
//				fontColor = "green";
//				comment = "사용 가능한 닉네임입니다.";
//				$("#user-update").attr("disabled", false);
//			}
//			$("#nicknameError").css("font-size", 7);
//			$("#nicknameError").css("color", fontColor);
//			$("#nicknameError").html(comment);
//		});
	},
	
	update: function(){
		// 영문, 한글, 숫자만 가능 But 2~8글자
		var pattern = /^([0-9]|[a-z]|[A-Z]|[가-힣]){2,8}$/;
		
		let data = {
			id: $("#id").val(),
			email: $("#email").text(),
			nickname: $("#nickname").val(),
			age_range: $('#age_range').val(),
			gender: $("#gender").val(),
			role: $("#role").val()
		};
		if(data.role != null && data.role != 'ROLE_ADMIN'){
			data.nickname = data.nickname.replace("👻","");
		}
		if(data.role == null || data.role==''){
			if(!pattern.test(data.nickname)){
				alert('2~8글자의 영문, 한글, 숫자만 사용가능합니다.');
				return false;
			}			
		}
		$.ajax({
			type: "PUT",
			url: "/api/user/"+data.id,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(){
			alert("수정이 완료되었습니다.");
//			location.href = "/user/updateForm";
			history.go(-1);
		}).fail(function(error){ 
			alert(JSON.stringify(error));
		})
	}
}

index.init();