<%@tag language="java" pageEncoding="UTF-8"%>

<%@attribute name="title" required="true" rtexprvalue="true" type="java.lang.String"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <c:url var="urlCss" value="/css/bootstrap.min.css" />
    <link href="${urlCss}" rel="stylesheet">
    
    <c:url var="urlJquery" value="/js/jquery-3.3.1.slim.js" />
    <script src="${urlJquery}"></script>
    
    <c:url var="urlBootstrapJs" value="/js/bootstrap.min.js" />
    <script src="${urlBootstrapJs}"></script>
    
    <c:url var="urlPopper" value="/js/popper.min.js" />
    <script src="${urlPopper}"></script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <span class="navbar-brand">Mail</span>
        <div class="collapse navbar-collapse justify-content-end" id="myNavbar">
            <c:if test="${not empty user}">
                <c:url var="urlLogout" value="/authorization/logout.html" />
                <span class="navbar-brand">${user.displayName}</span>
                <div class="navbar-nav">
                    <a href="${urlLogout}" class="btn btn-primary">Logout</a>
                </div>
            </c:if>
        </div>
    </nav>
    <c:if test="${not empty info}">
        <div class="alert col-4 mx-auto mt-4 alert-info alert-dismissible fade show">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <strong>Info!</strong> ${info}
        </div>
    </c:if>
    <c:if test="${not empty success}">
        <div class="alert col-4 mx-auto mt-4 alert-success alert-dismissible fade show">
            <button type="button" class="close" data-dismiss="alert">&times;</button>
            <strong>Success!</strong> ${success}
        </div>
    </c:if>
    <jsp:doBody />
</body>
</html>