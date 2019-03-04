<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:navigation-bar title="Messages">
	<ul title="Received messages">
		<c:forEach var="message" items="${receivedMessages}">
		<c:url var="urlEditMessage" value="/message/edit.html">
			<c:param name="id" value="${message.id}"/>
		</c:url>
			<a href="${urlEditMessage}" class="button">
				<li>
					<span class="content">${message.sender.displayName}</span>
					<br>
					<span class="content">${message.subject}</span>
				</li>
			</a>
		</c:forEach>
	</ul>
</u:navigation-bar>