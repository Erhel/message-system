<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="u" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<u:navigation-bar title="Write message">
	<c:url var="urlSaveMessage" value="/message/save.html"/>
	<c:url var="urlSendMessage" value="/message/send.html"/>
	<c:url var="urlBack" value="/message/list.html"/>
	<form method="post" id="saveForm">
		<label for="recipient">Recipient:</label>
		<input id="recipient" name="recipient" value="${message.recipient.username}">
		<br> 
		<label for="subject">Subject:</label>
		<input id="subject" name="subject" value="${message.subject}">
		<br> 
		<label for="message">Message:</label>
		<input id="message" name="message" value="${message.message}">
	</form>
	<button formaction="${urlSaveMessage}" form="saveForm" class="save">Submit</button>
	<button formaction="${urlSendMessage}" form="saveForm" class="send">Send</button>
	<a href="${urlBack}" class="button">Back</a>
</u:navigation-bar>