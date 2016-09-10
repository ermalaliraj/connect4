<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Datapine Login</title>
	<link href="/datapine-test-app/resources/css/screen.css" rel="stylesheet" type="text/css"/>
</head>

<body>
	<div id=container>
		<h1>Welcome, Please Login</h1>
	
		<form action="<c:url value='j_spring_security_check' />" method="POST">
			<table>
				<c:if test="${!error}">
					<tr>
						<td></td>
						<td>
							<label class="error">${error}</label>							
						</td>
					</tr>
				</c:if>
			
				<tr>
					<td>User:</td>
					<td><input type='text' name='j_username' value=''>
					</td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><input type='password' name='j_password' />
					</td>
				</tr>
				<tr>
					<td><input name="submit" type="submit"
						value="Login" />
					</td>
				
					<td><input name="reset" type="reset" />
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<a href="/datapine-test-app/register"> Register yourself</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>