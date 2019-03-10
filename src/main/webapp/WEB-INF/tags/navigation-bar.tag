<%@tag language="java" pageEncoding="UTF-8"%>

<%@attribute name="title" required="true" rtexprvalue="true"
	type="java.lang.String"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title}</title>
	<c:url var="urlCss" value="/css/bootstrap.min.css" />
	<link href="${urlCss}" rel="stylesheet">
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<span class="navbar-brand mb-0 h1">Mail</span>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="myNavbar" aria-controls="myNavbar" aria-expanded="false" aria-label="Toggle navigation">
    		<span class="navbar-toggler-icon"></span>
  		</button>
  		<div class="collapse navbar-collapse ml-auto w-100 justify-content-end" id="myNavbar">
			<c:choose>
				<c:when test="${not empty param.message}">
					<span class="navbar-text">${param.message}</span>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty user}">
						<c:url var="urlLogout" value="/authorization/logout.html"/>
						<span class="navbar-brand mb-0 h1">${user.displayName}</span>
						<div class="navbar-nav">
							<a href="${urlLogout}" class="btn btn-primary">Logout</a>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</nav>
	<jsp:doBody />
</body>
</html>