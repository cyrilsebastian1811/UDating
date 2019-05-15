<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${role eq 'U'}">
		<form:form action="registerUser" method="post" modelAttribute="user" enctype = "multipart/form-data">
			<div  style="text-align: center; padding: 30px;">
				<fieldset style="display: inline-block;">
					<legend>User-Registration-Form:</legend>
					<table>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Full Name:</legend>
									<form:input type="text" path="fullName" cssStyle="width: 100%; font-size: 1em; border: none;"/>
								</fieldset>
								<form:errors path="fullName" cssStyle="color: red;"/>
							</td>
						</tr>
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
									<legend>EmailID:</legend>
									<form:input type="text" path="emailId" cssStyle="width: 100%; font-size: 1em; border: none;"/>
								</fieldset>
								<form:errors path="emailId" cssStyle="color: red;"/>
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
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Age:</legend>
									<form:input type="text" path="age" cssStyle="width: 100%; font-size: 1em; border: none;"/>
								</fieldset>
								<form:errors path="age" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Age-Preference:</legend>
									<form:input type="text" path="agePreference" cssStyle="width: 100%; font-size: 1em; border: none;"/>
								</fieldset>
								<form:errors path="agePreference" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Sex:</legend>
									<form:select path="sex" cssStyle="width: 100%; font-size: 1em; border: none;">
										<form:option value="M" label="Male"/>
										<form:option value="F" label="Female"/>
									</form:select>
								</fieldset>
								<form:errors path="sex" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Sex-Preference:</legend>
									<form:select path="sexPreference" cssStyle="width: 100%; font-size: 1em; border: none;">
										<form:option value="F" label="Female"/>
										<form:option value="M" label="Male"/>
									</form:select>
								</fieldset>
								<form:errors path="sexPreference" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Bio:</legend>
									<form:textarea path="bio"/>
								</fieldset>
								<form:errors path="bio" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Street:</legend>
									<form:input type="text" path="location.street" cssStyle="width: 100%; font-size: 1em; border: none;"/>
								</fieldset>
								<form:errors path="location.street" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>City:</legend>
									<form:input type="text" path="location.city" cssStyle="width: 100%; font-size: 1em; border: none;"/>
								</fieldset>
								<form:errors path="location.city" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Country:</legend>
									<form:input type="text" path="location.country" cssStyle="width: 100%; font-size: 1em; border: none;"/>
								</fieldset>
								<form:errors path="location.country" cssStyle="color: red;"/>
							</td>
						</tr>
						<tr>
							<td>
								<fieldset style="padding: 0 5px 3px">
									<legend>Upload Picture:</legend>
									<input type="file" name="photo"/>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td><input type="submit" name="submit" value="register" /></td>
						</tr>
					</table>
				</fieldset>
			</div>
		</form:form>
		</c:when>
		
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</body>
</html>