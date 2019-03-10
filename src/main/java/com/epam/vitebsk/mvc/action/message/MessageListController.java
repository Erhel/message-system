package com.epam.vitebsk.mvc.action.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageListController extends Controller {

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		if (user!=null && user.getId()!=null) {
			
			MessageService service = serviceFactory.getMessageService();
			Long id = user.getId();
			
			List<Message> receivedMessages = service.findMessagesByRecipientId(id);
			List<Message> sentMessages = service.findMessagesBySenderId(id);
			req.setAttribute("receivedMessages", receivedMessages);
			req.setAttribute("sentMessages", sentMessages);
		}
		
		return null;
	}
}
