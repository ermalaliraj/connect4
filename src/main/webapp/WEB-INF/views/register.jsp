<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Datapine Registration</title>
<link href="/datapine-test-app/resources/css/screen.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<div id=container>
<h1>Registration</h1>

<form method="POST" action="/datapine-test-app/registerNew">
<table>
	<tr>
		<td>Email:</td>
        <td><input name="email" /></td>
	</tr>
	<tr>
		<td>Password:</td>
        <td><input name="password" /></td>
	</tr>	    	
	<tr>
		<td><input type="submit" value="Register" />
		</td>
		<td><input name="reset" type="reset" />
		</td>
	</tr>
	<tr>
		<td colspan='2'>
			<a href="/datapine-test-app/login"> Go to login</a>
		</td>
	</tr>
</table>
</form>
</div>

</body>
</html>