package com.epam.vitebsk.mvc.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Action;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageEditAction extends Action {

	@Override
	public Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		Long id = null;
		
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (NumberFormatException e) {}
		
		if (id!=null) {
			Message message = serviceFactory.getMessageService().find(id);
			
			HttpSession session = req.getSession(false);
			
			User currentUser = (User) session.getAttribute("user");
			
			User user = message.getSender();
			
			if (user.getId()!=currentUser.getId()) {
			    return new Response(true, "/message/list.html");
			}
			
			req.setAttribute("message", message);
		}
		
		return null;
	}

}
