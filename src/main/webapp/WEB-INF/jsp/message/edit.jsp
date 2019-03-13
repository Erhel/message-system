<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="u" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<u:navigation-bar title="Write message">
	<div class="container mt-4">
		<form method="post" id="saveForm">
            <c:if test="${not empty message.id}">                
                <c:set var="readonly" value="readonly"/>
            </c:if>
			<div class="form-group">
				<label for="recipient">Recipient:</label>

                <div class="input-group mb-3">
                    <input type="email" class="form-control" id="recipient" name="recipient" ${readonly} value="${message.recipient.username}">
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" id="button-recipients" data-toggle="modal" data-target="#recipientsModal">Recipients</button>
                    </div>
                </div>
			</div>

			<div class="form-group">
				<label for="subject">Subject:</label>
				<input type="text" class="form-control" id="subject" name="subject" ${readonly} value="${message.subject}">
			</div>
			<div class="form-group">
				<label for="message">Message:</label>
				<textarea class="form-control" rows="5" id="message" name="message" ${readonly}>${message.message}</textarea>
			</div>
		</form>
        <div class="text-right">
            <c:if test="${empty message.id}">
                <c:url var="href" value="/message/save.html"/>
                <button type="submit" formmethod="post" formaction="${href}" form="saveForm" onclick="closeTab()" class="btn btn-primary">Save</button>
                
                <c:url var="urlSendMessage" value="/message/send.html"/>
    			<button type="submit" formmethod="post" formaction="${urlSendMessage}" form="saveForm" onclick="closeTab()" class="btn btn-primary">Send</button>
            </c:if>
            <c:url var="urlBack" value="/message/list.html"/><a href="${urlBack}" onclick="closeTab()" class="btn btn-primary">Back</a>
        </div>
	</div>

    <div class="modal" id="recipientsModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header text-center">
                    <h5 class="modal-title">Recipients</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form>
                        <div class="form-group">
                            <select id="select-recipients" size="6" multiple="multiple" class="form-control">
                                <c:forEach var="username" items="${usernames}">
                                    <option>${username}</option>
                                </c:forEach>
                            </select>
                        </div>            
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button id="save-changes" type="button" class="btn btn-primary">Save</button>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        $('#recipientsModal').on('shown.bs.modal', function(){
    	  $('#button-recipients').trigger('focus');
    	});

        $('#save-changes').click(function(){
        	$('#recipientsModal').modal('hide')
            var val = $('#select-recipients').val();
            if(!$('#recipient').is('[readonly]')) {
                $('#recipient').val(val);
            }
        });
        function closeTab()
        {
          window.close();
        }
     </script>
</u:navigation-bar>