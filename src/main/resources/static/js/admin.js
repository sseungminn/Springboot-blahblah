var index = {
	init: function(){
		$("#admin-user-update").on("click", ()=>{ 
			this.updateUser();
		});
		
		$("#admin-board-update").on("click", ()=>{
			this.updateBoard();
		});
		
		$("#admin-reply-update").on("click", ()=>{
			this.updateReply();
		});
		
		$("#admin-report-update").on("click", ()=>{
			this.updateReport();
		});
	},
	
	updateUser: function(){
		let data = {
			id: $("#id").text(),
			email: $("#email").text(),
			nickname: $("#nickname").val(),
			role: $("#role").val()
		};
		console.log(data);
		$.ajax({
			type: "PUT",
			url: "/admin/api/user/"+data.id,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(){
			alert("수정이 완료되었습니다.");
			self.close();
			opener.parent.location.reload();
		}).fail(function(error){ 
			alert(JSON.stringify(error));
		})
	},
	
	updateBoard: function(){
		let data = {
			id: $("#id").text(),
			title: $("#title").val(),
			content: $("#content").val()
		};
		console.log(data);
		$.ajax({
			type: "PUT",
			url: "/admin/api/board/"+data.id,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(){
			alert("수정이 완료되었습니다.");
			self.close();
			opener.parent.location.reload();
		}).fail(function(error){ 
			alert(JSON.stringify(error));
		})
	},
	
	delete: function(target_type, target_id, a){
		check = confirm('삭제하시겠습니까?');
		if(!check){
			return false;
		}
		id = $("#id").text();
		if(target_type == 'reply'){
			url = '/admin/api/board/'+target_id+'/'+target_type+'/'+a;
		} else{
			url = '/admin/api/'+target_type+'/'+target_id
		}
		$.ajax({
			type: "DELETE",
			url: url,
			dataType: "json" 
		}).done(function(){
			location.reload();
		}).fail(function(error){ 
			alert(JSON.stringify(error));
		})
	},
	
	updateReply: function(){
		let data = {
			id: $("#id").val(),
			content: $("#content").val()
		};
		console.log(data);
		$.ajax({
			type: "PUT",
			url: "/admin/api/reply/"+data.id,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(){
			alert("수정이 완료되었습니다.");
			self.close();
			opener.parent.location.reload();
		}).fail(function(error){ 
			alert(JSON.stringify(error));
		})
	},
	updateReport: function(){
		let data = {
			id: $("#id").val(),
		};
		$.ajax({
			type: "PUT",
			url: `/report/api/${data.id}`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json"
		}).done(function(){
			alert('신고가 정상적으로 처리 되었습니다.');
			self.close();
			opener.parent.location.reload();
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
}

index.init();