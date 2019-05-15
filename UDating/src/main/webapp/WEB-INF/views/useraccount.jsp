<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link href="<c:url value='/resources/css/account.css'/>" rel="stylesheet" type="text/css"/>
		<script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg="
  		crossorigin="anonymous"></script>
	</head>
	<body>
		<div class="toolbar">
			<a href="userMessage"><span>💬</span></a>
			<a><span>🌎</span></a>
			<a href="userSettings"><span>⚙️</span></a>
			<a href="logout"><span>⛔</span></a>
		</div>
		<div class="container">
			<c:choose>
				<c:when test="${users != null}">
				      <c:forEach items="${users}" var = "person">
					    <div class="user">
					    	<img src="${person.photoFilePath}" width="100%">
					    	<div id="${ person.id }">
					    		<p>${person.fullName}</p>
					    		<p>${person.age} ${person.sex}</p>
					    		<p>Preferred Age: ${person.agePreference}</p>
					    		<p>Likes: ${person.sexPreference}</p>
					    		<p>Bio: ${person.bio}</p>
						    	<div>
						    		<button onclick="like(event)"><span>✔️️</span></button>&emsp;&emsp;
						    		<button onclick="report(event)"><span>report</span></button>&emsp;&emsp;
						    		<button onclick="dislike(event)"><span>❌️️</span></button>
						    	</div>
					    	</div>
					    </div>
				      </c:forEach>
				</c:when>
				<c:otherwise>
				    <div class="user">
				    	<h1>Sorry bud, No users around you</h1>
				    </div>
				</c:otherwise>
			</c:choose>
		</div>
		
		<script type="text/javascript">
			function like(event){
				let parentele = event.currentTarget.parentElement.parentElement;
				console.log(parentele);
				$.ajax({
				    url: "ajax/users/${account.id}",
				    method: 'PUT',
				    data: "profileId="+parentele.id+"&clicked=like",
				    success: function(data){
				    	console.log(data);
				    	alert(data);
						parentele.parentElement.remove();
					}
				});
			}
			
			function dislike(event){
				let parentele = event.currentTarget.parentElement.parentElement;
				console.log(parentele);
				$.ajax({
				    url: "ajax/users/${account.id}",
				    method: 'PUT',
				    data: "profileId="+parentele.id+"&clicked=dislike",
				    success: function(data){
				    	console.log(data);
				    	alert(data);
						parentele.parentElement.remove();
					}
				});
			}
			
			function report(event){
				let parentele = event.currentTarget.parentElement.parentElement.parentElement;
				console.log(parentele);
				$.ajax({
				    url: "ajax/users/${account.id}",
				    method: 'PUT',
				    data: "profileId="+parentele.id+"&clicked=report",
				    success: function(data){
				    	alert(data["message"]);
						parentele.parentElement.remove();
					}
				});
			}
		
		</script>
	</body>
</html>