package com.epam.vitebsk.mvc.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageEditController extends Controller {

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		Long id = null;
		
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (NumberFormatException e) {}
		
		if (id!=null) {
			Message message = serviceFactory.getMessageService().find(id);
			
			HttpSession session = req.getSession(false);
			
			User currentUser = (User) session.getAttribute("user");
			
			User sender = message.getSender();
			User recipient = message.getRecipient();
			
			if (sender.getId()!=currentUser.getId() && recipient.getId()!=currentUser.getId()) {
			    return new Response(true, "/message/list.html");
			}
			
			req.setAttribute("message", message);
		}
		
		return null;
	}

}
