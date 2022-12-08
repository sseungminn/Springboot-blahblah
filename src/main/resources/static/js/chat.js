var ws;

	function wsOpen(){
		//웹소켓 전송시 현재 방의 번호를 넘겨서 보낸다.
		ws = new WebSocket("ws://" + location.host + "/chating/"+$("#roomNumber").val());
		wsEvt();
	}
		
	function wsEvt() {
		ws.onopen = function(data){
			//소켓이 열리면 동작
		}
		
		ws.onmessage = function(data) {
			//메시지를 받으면 동작
			var msg = data.data;
			var today = new Date();
			var h = ('0' + today.getHours()).slice(-2);
			var m = ('0' + today.getMinutes()).slice(-2);
//			console.log(data);
//			console.log(typeof(msg));
//			console.log(msg);
			if(msg != null && msg.type != ''){
				
				//파일 업로드가 아닌 경우 메시지를 뿌려준다.
				if(JSON.parse(msg).type == "getId"){
					var si = JSON.parse(msg).sessionId != null ? JSON.parse(msg).sessionId : "";
					if(si != ''){
						$("#sessionId").val(si);
					}
				}else if(JSON.parse(msg).type == "message"){
					if(JSON.parse(msg).sessionId == $("#sessionId").val()){
						console.log(JSON.parse(msg).msg.replace('<',"&lt;").replace('>',"&gt;").replace('"',"&quot;").replace("'","&#39;").replace("\n","<br />").replace("</","&lt;&#47;").replace("<br>",""));
						if($(".time_me").last().html() == h+':'+m){ // 1분 내로 보냈을 경우에
							$("#chating").append("<div class='me'> " + JSON.parse(msg).msg.replace('<',"&lt;").replace('>',"&gt;").replace('"',"&quot;").replace("'","&#39;").replace("\n","<br />").replace("</","&lt;&#47;").replace("<br />","") + " </div><br/>");
						} else{
							$("#chating").append("<div class='myNickname'>" + JSON.parse(msg).userName + "</div><br/>");
							$("#chating").append("<div class='me'> " + JSON.parse(msg).msg.replace('<',"&lt;").replace('>',"&gt;").replace('"',"&quot;").replace("'","&#39;").replace("\n","<br />").replace("</","&lt;&#47;").replace("<br />","") + " </div><br/>");
							$("#chating").append("<div class='time_me'>" + h +":"+ m + "</div><br/>");
						}
					}else{
						if($(".time_others").last().html() == h+':'+m){ // 1분 내로 보냈을 경우에
							$("#chating").append("<div class='others'> " + JSON.parse(msg).msg.replace('<',"&lt;").replace('>',"&gt;").replace('"',"&quot;").replace("'","&#39;").replace("\n","<br />").replace("</","&lt;&#47;").replace("<br />","") + " </div><br/>");
						} else{
							$("#chating").append("<div class='otherNickname'> " + JSON.parse(msg).userName + "</div><br/>");
							$("#chating").append("<div class='others'> " + JSON.parse(msg).msg.replace('<',"&lt;").replace('>',"&gt;").replace('"',"&quot;").replace("'","&#39;").replace("\n","<br />").replace("</","&lt;&#47;").replace("<br />","") + " </div><br/>");
							$("#chating").append("<div class='time_others'>" + h +":"+ m + "</div><br/>");
						}
					}
						
				} else{
					console.warn("unknown type!");
				}
			}else{ // 파일업로드
					var url = URL.createObjectURL(new Blob([msg]));
					$("#chating").append("<div class='img'><img class='msgImg' src="+url+"></div><div class='clearBoth'></div>");
					document.getElementById('fileUpload').value = '';
			}
		gotoBottom();	
		}
	}
	
//	function keyUpCheck(){
//		$("#chatting").keyup(function(){
//			var newText = $("#chatting").val();
//			console.log(newText);
//			if(newText =='' || newText==null){
//				continue;
//			}else{
//				return true;
//			}
//		});
//	}
	document.addEventListener("keypress", function(e){
		if(e.keyCode == 13){ //enter press
			send();
			gotoBottom();
		}
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
	

	function chatName(){
		var userName = $("#userName").val();
		if(userName == null || userName.trim() == ""){
			alert("사용자 이름을 입력해주세요.");
			$("#userName").focus();
		}else{
			wsOpen();
			$("#yourName").hide();
			$("#yourMsg").show();
		}
	}
	
	
	function send() {
		var option ={
			type: "message",
			roomNumber: $("#roomNumber").val(),
			sessionId : $("#sessionId").val(),
			userName : $("#userName").val(),
			msg : $("#chatting").val()
		}
		ws.send(JSON.stringify(option));
		$('#chatting').val('');
	}

	function fileSend(){
		var file = document.querySelector("#fileUpload").files[0];
		var fileReader = new FileReader();
		fileReader.onload = function() {
			var param = {
				type: "fileUpload",
				roomNumber: $("#roomNumber").val(),
				sessionId : $("#sessionId").val(),
				msg : $("#chatting").val(),
				userName : $("#userName").val(),
				file: file
			}
			ws.send(JSON.stringify(param)); //파일 보내기전 메시지를 보내서 파일을 보냄을 명시한다.
	    	arrayBuffer = this.result;
			ws.send(arrayBuffer); //파일 소켓 전송
		};
		fileReader.readAsArrayBuffer(file);
	}
	
	// 채팅 올라오면 스크롤 아래로 내리는 함수
	function gotoBottom(){
		var element = document.getElementById("chating");
		var scrollTop = $("#chating").scrollTop(); //현재 스크롤영역의 맨 위
	    var innerHeight = $("#chating").innerHeight(); //패딩값을 포함한 현재 div의 높이
	    var scrollHeight = $("#chating").prop('scrollHeight'); //현재 div의 스크롤된 모든 영역의 높이(보이지 않는 스크롤된 영역 높이 포함)
//	    console.log("현재 스크롤 영역의 맨 위? : " + scrollTop);
//	    console.log("패딩값을 포함한 현재 div의 높이 : " + innerHeight);
//	    console.log("현재 div의 스크롤된 모든 영역의 높이 : " + scrollHeight);
//	    console.log(scrollHeight - (scrollTop + innerHeight));
//	    console.log("-----------------------------------------------");
		if(scrollHeight - (scrollTop + innerHeight) < 22){ // 맨 아래에 스크롤이 있다면.
			element.scrollTop = element.scrollHeight;
		} else {
			return false;
		}
	}
	
	function clock(){
	    var now = new Date();
	    var years = now.getFullYear();
	    var months = now.getMonth();
	    var days = now.getDate();
	    var dow = now.getDay(); //day of week, 0~6, 0==일
	    var weeks = ['일', '월', '화', '수', '목', '금', '토'];
	    var hours = ('0' + now.getHours()).slice(-2);
	    var minutes = ('0' + now.getMinutes()).slice(-2);
	    var seconds = ('0' + now.getSeconds()).slice(-2);
	    if(hours > 12){
	    	var obj = $(".now").text(years + '년 ' + (months+1) +'월 '+ days + '일 ' + weeks[dow] + '요일 \n오후 ' + (hours-12) + '시 ' + minutes + '분 ' + seconds + '초');
	    } else{
			var obj = $(".now").text(years + '년 ' + (months+1) +'월 '+ days + '일 ' + weeks[dow] + '요일 \n오전 ' + hours + '시 ' + minutes + '분 ' + seconds + '초');
		}
		obj.html(obj.html().replace(/\n/g, '<br/>'));
  	}
	setInterval(clock, 1000);
