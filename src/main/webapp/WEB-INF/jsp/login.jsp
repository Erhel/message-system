<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="u" %>

<u:navigation-bar title="Login">
<body>
	<h2>Login</h2>
	<c:url var="urlLogin" value="/login.html" />
	<form action="${urlLogin}" method="post">
		<label for="login">Login:</label>
		<input id="login" name="login">
		<label for="password">Password:</label>
		<input id="password" name="password">
		<button class="login">Log In</button>
	</form>
</body>
</u:navigation-bar>