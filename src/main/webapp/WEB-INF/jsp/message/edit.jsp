<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="u" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<u:navigation-bar title="Write message">
	<div class="container">
		<div class="row">
			<div class="col">
				<form method="post" id="saveForm">
					<div class="form-group">
						<label for="recipient">Recipient:</label>
						<!--input type="email" class="form-control" id="recipient" name="recipient" value="${message.recipient.username}"-->

                        <div class="input-group mb-3">
                            <input type="email" class="form-control" id="recipient" name="recipient" value="${message.recipient.username}">
                            <div class="input-group-append">
                                <button class="btn btn-outline-secondary" type="button" id="button-recipients" data-toggle="modal" data-target="#recipientsModal">Recipients</button>
                            </div>
                        </div>
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
        <div class="text-right">
            <c:url var="href" value="/message/save.html">
                <c:param name="send" value="false"/>
                <c:if test="${not empty message}">
                    <c:param name="id" value="${message.id}"/>
                </c:if>
            </c:url>
            <button type="submit" formmethod="post" formaction="${href}" form="saveForm" class="btn btn-primary">To drafts</button>

            <c:url var="urlSendMessage" value="/message/save.html">
                <c:param name="send" value="true"/>
                <c:if test="${not empty message}">
                    <c:param name="id" value="${message.id}"/>
                </c:if>
            </c:url>
			<button type="submit" formmethod="post" formaction="${urlSendMessage}" form="saveForm" class="btn btn-primary">Send</button>

            <c:url var="urlBack" value="/message/list.html"/><a href="${urlBack}" class="btn btn-primary">Back</a>
        </div>
	</div>

    <div class="modal" id="recipientsModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Recipients</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                <form>
                    <div class="form-group">
                        <select id="select-recipients" multiple="multiple" class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                    </div>            
                </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button id="save-changes" type="button" class="btn btn-primary">Save changes</button>
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

        	$('#recipient').val(val);
        });
     </script>
</u:navigation-bar>