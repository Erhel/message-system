package com.epam.vitebsk.mvc.action.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Action;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageListAction extends Action {

	@Override
	public Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {		
		HttpSession session = req.getSession(false);
		User user = (User) session.getAttribute("user");
		if (user!=null && user.getId()!=null) {
			List<Message> messages = serviceFactory.getMessageService().findMessagesBySenderId(user.getId());
			System.out.println("ok");
			req.setAttribute("receivedMessages", messages);
		}
		return null;
	}
}
