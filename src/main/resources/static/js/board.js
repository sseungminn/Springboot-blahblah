var index={
	init: function(){
		$("#board-save").on("click", ()=>{
			this.save();
		});
		$("#board-delete").on("click", ()=>{
			check = confirm("삭제하시겠습니까?");
			if(check==true){ 
			this.deleteById();
			}
		});
		$("#board-update").on("click", ()=>{ 
			this.update();
		});
//		$("#comment-save").on("click", ()=>{ 
//			this.commentSave();
//		});
		$("#reply-save").on("click", ()=>{ 
			this.replySave();
		});
		// textarea 크기 조정
		$(window).resize(function(){
			let modalWidth   = $(window).width();
			let modalHeight  = $(window).height();
			var $t = $(".modal-wrap");
			$t.find("textarea").width(modalWidth*0.4-10).height(modalHeight*0.4-70)
		});
//		$("#reply-content").parent().resize(function(){
//			var $t = $(this);
//			$t.find('textarea').width($t.parent().width()*0.4-10).height($t.height()-40)
//		}).resize();
		
		$('#reply-content').keyup(function (e) {
			let content = $(this).val();
		    // 글자수 세기
		    $('.textCount').css('font-size','8pt').css('font-color','grey');
		    if (content.length == 0 || content == '') {
		    	$('#reply-text-count').text('0자/200자');
		    } else {
		    	$('#reply-text-count').text(content.length + '자/200자');
		    }
		    // 글자수 제한
		    if (content.length > 200) {
		    	// 200자 부터는 타이핑 되지 않도록
		        $(this).val($(this).val().substring(0, 200));
		        // 200자 넘으면 알림창 뜨도록
		        alert('글자수는 200자까지 입력 가능합니다.');
		        $('#reply-text-count').text('200자/200자');
		    };
		});
		
		$('#comment-content').keyup(function (e) {
			let content = $(this).val();
		    // 글자수 세기
		    $('.textCount').css('font-size','8pt').css('font-color','grey');
		    if (content.length == 0 || content == '') {
		    	$('#comment-text-count').text('0자/200자');
		    } else {
		    	$('#comment-text-count').text(content.length + '자/200자');
		    }
		    // 글자수 제한
		    if (content.length > 200) {
		    	// 200자 부터는 타이핑 되지 않도록
		        $(this).val($(this).val().substring(0, 200));
		        // 200자 넘으면 알림창 뜨도록
		        alert('글자수는 200자까지 입력 가능합니다.');
		        $('#comment-text-count').text('200자/200자');
		    };
		});
		// 투표마감일 내일부터 선택되게끔
		let year = new Date().getFullYear();
		let month = new Date().getMonth()+1;
		let dt = new Date().getDate();
		month = month.toString().padStart(2,'0');
		let date = (dt+1).toString().padStart(2,'0');
		dt = dt.toString().padStart(2,'0');
		let tomorrow = year+"-"+month+"-"+date;
		let nextYear = year+1+"-"+month+"-"+dt;
		document.getElementById("deadline").setAttribute("min", tomorrow);
		document.getElementById("deadline").setAttribute("max", nextYear);
		
	},
	
	save: function(){
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
			deadline: $("#deadline").val(),
			user_id: $("#user_id").val()
		};
		let today = new Date();
		let year = today.getFullYear();
		let month = today.getMonth()+1;
		let date = today.getDate();
		month = month.toString().padStart(2,'0');
		date = date.toString().padStart(2,'0');
		today = year+"-"+month+"-"+date;
		
		//최대날짜(1년) 이후로 선택했나?
		let deadlineDate = new Date(data.deadline);
		let todayDate = new Date(today);
		let diffDate = Math.abs((deadlineDate.getTime() - todayDate.getTime())/(1000*60*60*24));
		
		if(data.deadline <= today || diffDate > 365){
			alert('날짜를 다시 입력해주세요.');
			return false;
		}
		if (data.title.length > 30) {
	    	// 30자 부터는 타이핑 되지 않도록
	        $("#title").val($("#title").val().substring(0, 30));
	        // 30자 넘으면 알림창 뜨도록
	        alert('토론주제는 30자까지 입력 가능합니다.');
	        return false;
	    };
	    if (data.title.length == 0) {
	        alert('토론주제는 필수 입력 사항입니다.');
	        return false;
	    };
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(){
			console.log(data);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	deleteById: function(){
		let id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: `/api/board/${id}`,
			dataType: "json"
		}).done(function(){ 
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	update: function(){
		let id = $("#id").val();
		let page = new URLSearchParams(location.search).get('page');
		let search = new URLSearchParams(location.search).get('search');
		let type = new URLSearchParams(location.search).get('type');
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
			deadline: $("#deadline").val()
		};
		let today = new Date();
		let year = today.getFullYear();
		let month = today.getMonth()+1;
		let date = today.getDate();
		month = month.toString().padStart(2,'0');
		date = date.toString().padStart(2,'0');
		today = year+"-"+month+"-"+date;
		//최대날짜(1년) 이후로 선택했나?
		let deadlineDate = new Date(data.deadline);
		let todayDate = new Date(today);
		let diffDate = Math.abs((deadlineDate.getTime() - todayDate.getTime())/(1000*60*60*24));
		
		if(data.deadline <= today || diffDate > 365){
			alert('날짜를 다시 입력해주세요.');
			return false;
		}
		if (data.title.length > 30) {
			console.log(data.title);
	    	// 30자 부터는 타이핑 되지 않도록
	        $("#title").val($("#title").val().substring(0, 30));
	        // 30자 넘으면 알림창 뜨도록
	        alert('토론주제는 30자까지 입력 가능합니다.');
	        return false;
	    };
	    if (data.title.length == 0) {
	        alert('토론주제는 필수 입력 사항입니다.');
	        return false;
	    };
		$.ajax({
			type: "PUT",
			url: `/api/board/${id}`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
		}).done(function(){ 
//			alert("글수정이 완료되었습니다.");
			location.href = `/auth/board/${id}?page=${page}&search=${search}&type=${type}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	//댓글저장
	commentSave: function(){
		let checkOpinion = $("input[name='opinion']:checked").val();
		if(checkOpinion == undefined){
			alert('찬반여부를 선택해주세요.');
			return false;
		}
		let data = {
			user_id: $("#user_id").val(),
			board_id: $("#board_id").val(),
			content: $("#comment-content").val(),
			opinion: $("input[name='opinion']:checked").val()
		};
		if(data.opinion == null || data.opinion == ""){
			alert('찬반여부를 선택해주세요.');
			return false;
		}
		if(data.content == null || data.content == ""){
			alert('의견을 적어주세요.');
			return false;
		}
		let page = new URLSearchParams(location.search).get('page');
		let search = new URLSearchParams(location.search).get('search');
		let type = new URLSearchParams(location.search).get('type');
		$.ajax({
			type: "POST",
			url: `/api/board/${data.board_id}/comment`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
		}).done(function(){ 
//			alert("댓글쓰기가 완료되었습니다.");
			location.href = `/auth/board/${data.board_id}?page=${page}&search=${search}&type=${type}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	//답글저장
	replySave: function(){
		let checkOpinion = $("input[name='reply-opinion']:checked").val();
		if(checkOpinion == undefined){
			alert('찬반여부를 선택해주세요.');
			return false;
		}
		let data = {
			user_id: $("#user_id").val(),
			board_id: $("#board_id").val(),
			content: $("#reply-content").val(),
			bundle: $("#bundle").val(),
			step: $("#step").val(),
			opinion: $("input[name='reply-opinion']:checked").val()
		};
		if(data.content == null || data.content == ""){
			alert('의견을 적어주세요.');
			return false;
		}
		let page = new URLSearchParams(location.search).get('page');
		let search = new URLSearchParams(location.search).get('search');
		let type = new URLSearchParams(location.search).get('type');
		$.ajax({
			type: "POST",
			url: `/api/board/${data.board_id}/reply`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
		}).done(function(){ 
//			alert("댓글쓰기가 완료되었습니다.");
			location.href = `/auth/board/${data.board_id}?page=${page}&search=${search}&type=${type}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	commentDelete: function(board_id, comment_id){
		let page = new URLSearchParams(location.search).get('page');
		let search = new URLSearchParams(location.search).get('search');
		let type = new URLSearchParams(location.search).get('type');
		$.ajax({
			type: "PUT",
			url: `/api/board/${board_id}/comment/${comment_id}`,
			dataType: "json"
		}).done(function(){ 
//			alert("댓글삭제 성공");
			location.href = `/auth/board/${board_id}?page=${page}&search=${search}&type=${type}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
	replyDelete: function(board_id, reply_id){
		let page = new URLSearchParams(location.search).get('page');
		let search = new URLSearchParams(location.search).get('search');
		let type = new URLSearchParams(location.search).get('type');
		$.ajax({
			type: "PUT",
			url: `/api/board/${board_id}/reply/${reply_id}`,
			dataType: "json"
		}).done(function(){ 
			location.href = `/auth/board/${board_id}?page=${page}&search=${search}&type=${type}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}

index.init();