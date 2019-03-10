<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="u" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<u:navigation-bar title="Write message">
	<c:url var="urlSaveMessage" value="/message/save.html">
        <c:param name="send" value="false"/>
        <c:if test="${not empty message}">
            <c:param name="id" value="${message.id}"/>
        </c:if>
    </c:url>
	<c:url var="urlSendMessage" value="/message/save.html">
        <c:param name="send" value="true"/>
        <c:if test="${not empty message}">
            <c:param name="id" value="${message.id}"/>
        </c:if>
    </c:url>
	<c:url var="urlBack" value="/message/list.html"/>
	<div class="container">
		<div class="row">
			<div class="col">
				<form method="post" id="saveForm">
					<div class="form-group">
						<label for="recipient">Recipient:</label>
						<input type="email" class="form-control" id="recipient" name="recipient" value="${message.recipient.username}">
					</div>
					<div class="form-group">
						<label for="subject">Subject:</label>
						<input type="text" class="form-control" id="subject" name="subject" maxlength="256" value="${message.subject}">
					</div>
					<div class="form-group">
						<label for="message">Message:</label>
						<textarea class="form-control" rows="5" id="message" name="message" maxlength="1024">${message.message}</textarea>
					</div>
				</form>
			</div>
		</div>
		<div class="row justify-content-between">
			<div class="col-2">
				<button type="submit" formmethod="post" formaction="${urlSaveMessage}" form="saveForm" class="btn btn-primary btn-block">To drafts</button>
			</div>
			<div class="col-2">
				<button type="submit" formmethod="post" formaction="${urlSendMessage}" form="saveForm" class="btn btn-primary btn-block">Send</button>
			</div>
		</div
		<div class="row">
			<div class="col-3 mx-auto">
				<a href="${urlBack}" class="btn btn-primary btn-block">Back</a>
			</div>
		</div>
	</div>
</u:navigation-bar>