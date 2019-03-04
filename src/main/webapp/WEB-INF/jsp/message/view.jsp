<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="u" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<u:navigation-bar title="Write message">
  <body>
    <label for="recipient" >Subject:</label>
    <div id="recipient">${message.recipient}</div>
    <br>
    <label for="subject" >Subject:</label>
    <div id="subject">${message.subject}</div>
    <br>
    <label for="message" >Subject:</label>
    <div id="message">${message.message}</div>
  </body>
</u:navigation-bar>