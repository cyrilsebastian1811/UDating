<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<form:form action="" method="post" modelAttribute="login">
		<div style="text-align: center; padding: 30px;">
			<fieldset style="display: inline-block;">
			  	<legend>Login:</legend>
				<table>
					<tr>
						<td>
							<fieldset style="padding: 0 5px 3px">
								<legend>AccountID:</legend>
								<form:input type="text" path="accountId" cssStyle="width: 100%; font-size: 1em; border: none;"/>
							</fieldset>
							<form:errors path="accountId" cssStyle="color: red;"/>
						</td>
					</tr>
					<tr>
						<td>
							<fieldset style="padding: 0 5px 3px">
								<legend>Password:</legend>
								<form:input type="password" path="password" cssStyle="width: 100%; font-size: 1em; border: none;"/>
							</fieldset>
							<form:errors path="password" cssStyle="color: red;"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<input type="submit" name ="submit" value="login"/>&emsp;&emsp;
							<a href="${pageContext.request.contextPath}/changePassword">Forgot Password</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<a href="${pageContext.request.contextPath}/register?role=U">Create an User Account</a>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<a href="${pageContext.request.contextPath}/register?role=M">Create an Marketer Account</a>
						</td>
					</tr>				
				</table>
			</fieldset>
		</div>
	</form:form>
</body>
<scrip>
	
</scrip>
</html>
