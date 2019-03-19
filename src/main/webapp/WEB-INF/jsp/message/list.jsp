<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:navigation-bar title="Messages">
    <div class="container">
        <div class="text-center mt-4">
            <c:url var="urlEditMessage" value="/message/edit.html" />
            <a href="${urlEditMessage}" class="btn btn-primary">Write message</a>
        </div>
        <div class="row">
            <div class="list-gpoup col" title="Received messages">
                <h3 class="text-center">Received messages</h3>
                <c:forEach var="message" items="${receivedMessages}">
                    <c:url var="urlEditMessage" value="/message/edit.html">
                        <c:param name="id" value="${message.id}" />
                    </c:url>
                    <a href="${urlEditMessage}" target="_blank"
                        class="list-group-item list-group-item-action flex-column align-items-start">
                        <h5 class="mb-1">From: ${message.sender.username}</h5>
                        <p class="mb-1">${message.subject}</p>
                    </a>
                </c:forEach>
            </div>
            <div class="list-gpoup col" title="Sent messages">
                <h3 class="text-center">Sent messages</h3>
                <c:forEach var="message" items="${sentMessages}">
                    <c:url var="urlEditMessage" value="/message/edit.html">
                        <c:param name="id" value="${message.id}" />
                    </c:url>
                    <a href="${urlEditMessage}" target="_blank"
                        class="list-group-item list-group-item-action flex-column align-items-start">
                        <h5 class="mb-1">To: ${message.recipient.username}</h5>
                        <p class="mb-1">${message.subject}</p>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</u:navigation-bar>