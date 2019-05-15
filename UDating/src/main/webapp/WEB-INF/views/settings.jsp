<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="<c:url value='/resources/css/settings.css'/>" rel="stylesheet" type="text/css"/>
<script src="https://code.jquery.com/jquery-3.4.0.min.js" integrity="sha256-BJeo0qm959uMBGb65z40ejJYGSgR7REI4+CW1fNKwOg=" crossorigin="anonymous"></script>
</head>

<body>
	<p>${ account.fullName }</p>
	<c:choose>
	    <c:when test="${page eq 'settings' and role eq 'U'}">
			<div class="toolbar">
				<a href="userMessage"><span>💬</span></a>
				<a  href="userAccount"><span>🌎</span></a>
				<a><span>⚙️</span></a>
				<a href="logout"><span>⛔</span></a>
			</div>
			<form:form action="userSettings" method="post" modelAttribute="newUser" enctype = "multipart/form-data">
				<div style="text-align: center; padding: 30px;">
			    	<fieldset style="display: inline-block;">
						<legend>Settings:</legend>
						<table>
							<tr>
								<td>
									<fieldset>
										<legend>Age-Preference:</legend>
										<form:input type="text" path="agePreference" value="${account.agePreference}"/>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td>
									<fieldset>
										<legend>Sex-Preference:</legend>
										<form:input type="text" path="sexPreference" value="${account.sexPreference}"/>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td>
									<fieldset>
										<legend>Bio:</legend>
										<form:textarea path="bio" value="${account.bio}" rows="5" cols="30" />
									</fieldset>
								</td>
							</tr>
							<tr>
								<td>
									<fieldset>
										<legend>Image:</legend>
										<input type="file" name="photo"/>
									</fieldset>
								</td>
							</tr>
							<tr>
								<td>
									<input type="submit" name="submit" value="update"/>
								</td>
							</tr>
						</table>
					</fieldset>
				</div>
			</form:form>
 	    </c:when>
 	    <c:when test="${page eq 'changePSWD'}">
		<form:form action="changePassword" method="post" modelAttribute="login">
		    	<fieldset>
					<legend>Change Password:</legend>
					<tr>
						<td>
							<fieldset style="padding: 0 5px">
								<legend>AccountID:</legend>
								<form:input type="text" path="accountId" cssStyle="width: 100%; font-size: 1em; border: none;"/>
							</fieldset>
							<form:errors path="accountId" cssStyle="color: red;"/>
						</td>
					</tr>
					<tr>
						<td>
							<fieldset style="padding: 0 5px">
								<legend>EmailID:</legend>
								<form:input type="text" path="emailId" cssStyle="width: 100%; font-size: 1em; border: none;"/>
							</fieldset>
							<form:errors path="emailId" cssStyle="color: red;"/>
						</td>
					</tr>
					<tr>
						<td>
							<fieldset style="padding: 0 5px">
								<legend>New-Password:</legend>
								<form:input type="text" path="password" cssStyle="width: 100%; font-size: 1em; border: none;"/>
							</fieldset>
							<form:errors path="password" cssStyle="color: red;"/>
						</td>
					</tr>
					<tr>
						<td><input type="submit" name="submit" value="confirm"/></td>
					</tr>
					</table>
				</fieldset>
			</form:form>
 	    </c:when>
	</c:choose>
</div>
</body>
</html>