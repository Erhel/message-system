package com.epam.vitebsk.mvc.controller.message;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageEditController implements Controller {

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
	    
	    Long id = null;
		
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (NumberFormatException e) {}
		
		MessageService messageService = serviceFactory.getMessageService();
		
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("user");
        
        Message message = null;
	    
	    Set<String> usernames = messageService.findUsernamesByUserId(currentUser.getId());
		
        req.setAttribute("usernames", usernames);
        
		if (id!=null) {
			message = messageService.find(id);
			
			User sender = message.getSender();
			User recipient = message.getRecipient();
			
			if (sender.getId()!=currentUser.getId() && recipient.getId()!=currentUser.getId()) {
			    return new Response("/message/list.html");
			}
			
			req.setAttribute("message", message);
		} else {
		    message = (Message) session.getAttribute("message");
		    if (message!= null) {
		        session.setAttribute("message", null);
		        req.setAttribute("message", message);
		    }
		}
		
		return null;
	}
}
