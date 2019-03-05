<%@tag language="java" pageEncoding="UTF-8"%>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <c:url var="urlCss" value="/css/bootstrap.min.css"/>
    <link href="${urlCss}" rel="stylesheet">
</head>
<body>

<h1>Mail</h1>
	<jsp:doBody/>
</body>
</html>