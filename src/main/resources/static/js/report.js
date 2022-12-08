var report = {
	init: function(){
		$("#report-post").on("click", ()=>{ 
			this.reportPost();
		});
		function noEvent() {    
			if (event.keyCode == 116 || event.keyCode == 123) {        
				event.keyCode= 2;        
				return false;    
			} else if(event.ctrlKey && (event.keyCode==78 || event.keyCode == 82)) {        
				return false;
			}
		}
		document.onkeydown = noEvent;
	},
	
	reportPost: function(){
		let data = {
			targetId: $("#target_id").val(),
			targetType: $("#target_type").val(),
			reportCode: $("#report_code").val(),
			content: $("#content").val()
		};
		console.log(data);
		$.ajax({
			type: "POST",
			url: `/report/api/${data.target_type}`,
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(){
			alert('신고가 정상적으로 접수 되었습니다.');
			self.close();
			opener.parent.location.reload();
		}).fail(function(error){ 
			alert(JSON.stringify(error));
		})
	},
	
	reportDelete: function(id){
		check = confirm('삭제하시겠습니까?');
		if(!check){
			return false;
		}
		$.ajax({
			type: "DELETE",
			url: `/report/api/${id}`,
			dataType: "json"
		}).done(function(){ 
			location.href="/admin_report";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
	
}

report.init();