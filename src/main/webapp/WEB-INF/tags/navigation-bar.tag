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

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

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