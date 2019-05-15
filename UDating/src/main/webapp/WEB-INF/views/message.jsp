<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="<c:url value='/resources/css/message.css'/>" rel="stylesheet" type="text/css"/>
		<script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.js" type="text/javascript" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js" type="text/javascript" crossorigin="anonymous"></script>
	</head>
	<body>
		<div class="toolbar">
			<a><span>💬</span></a>
			<a  href="userAccount"><span>🌎</span></a>
			<a href="userSettings"><span>⚙️</span></a>
			<a href="logout"><span>⛔</span></a>
		</div>
		<div class="container">
			<div id="profiles">
				<c:choose>
					<c:when test="${profiles != null}">
					      <c:forEach items="${profiles}" var = "person">
						    <div id="${person.id}" class="user" onclick="getMessages(event)">
					    		<img src="${person.photoFilePath}">&emsp;
					    		<p class="details"><span>Name: ${person.fullName}&emsp;Age: ${person.age}&emsp;Sex: ${person.sex}</span></p>
						    </div>
					      </c:forEach>
					</c:when>
					<c:otherwise>
					    <div class="user">
					    	<h1>You have not liked any one yet</h1>
					    </div>
					</c:otherwise>
				</c:choose>
			</div>
    		<div id="messages">
    			<div id="chatbox">
    			</div>
    			<textarea cols="100" rows="2" name="message"></textarea>
    			<button onclick="send(event)">send</button>
    		</div>
		</div>
		
		<script type="text/javascript">
			var chatbox = document.getElementById("chatbox");
			var typed = $('textarea')[0];
			var profileId;
			
			function connect() {
				var socket = new SockJS('http://localhost:8080/controller/chat');
				stompClient = Stomp.over(socket);
				stompClient.connect({
			           "user" : ${account.id}
				}, function(frame) {
					console.log("connected: " + frame);
					stompClient.subscribe('/user/queue/reply', function(response) {
						var data = JSON.parse(response.body);
						span="<span class='to'>";
						chatbox.innerHTML += span+data.msg+"</span>";
					});
				});
			}
			
			function getMessages(event){
				profileId = event.currentTarget.id;
				chatbox.innerHTML = '';
				connect();
				$.ajax({
				    url: "ajax/messages?userId=${account.id}&profileId="+profileId,
				    method: 'GET',
					success: function(data) {
						data = JSON.parse(data);
						for (let i = 0; i < data.length; i++) { 
							let span;
							if(data[i].fromId === '${account.id}') {
								span="<span class='from'>";
							}
							else span="<span class='to'>";
							chatbox.innerHTML += span+data[i].msg+"</span>";
						}
					}
				});
			}
			
			function send(event){
				stompClient.send("/app/message", {}, JSON.stringify({
					'fromId': '${account.id}',
					'toId': profileId,
					'msg': typed.value
				}));
				chatbox.innerHTML+="<span class='from'>"+typed.value+"</span>";
				typed.value="";
			}
			
			function disconnect(){
				stompClient.disconnect();
			}
		</script>
	</body>
</html>