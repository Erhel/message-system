<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib tagdir="/WEB-INF/tags" prefix="u"%>

<u:navigation-bar title="Messages">
	<body>

		<ul title="Received messages">
			<c:forEach var="message" items="${receivedMessages}">
				<li>
					<span class="content">${message.sender.displayName}</span>
					<span class="content">${message.subject}</span>
				</li>
			</c:forEach>
		</ul>
	</body>
</u:navigation-bar>