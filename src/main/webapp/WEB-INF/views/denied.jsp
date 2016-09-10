<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Datapine Access denied</title>
	<link href="/datapine-test-app/resources/css/screen.css" rel="stylesheet" type="text/css"/>
</head>

<body>
	<div id=container>
		<div class="dualbrand">
			Email: ${sessionScope.userName}
			Role: ${sessionScope.role}
		</div>
	
		<h1>${msg}</h1>
	</div>
	
	<p id="footer">
		<a href="${pageContext.request.contextPath}/home">Home page</a>
		<a href="/datapine-test-app/j_spring_security_logout">Logout</a>
	</p>
</body>
</html>