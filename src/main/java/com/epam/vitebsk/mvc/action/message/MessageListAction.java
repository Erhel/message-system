package com.epam.vitebsk.mvc.action.message;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.mvc.Action;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageListAction extends Action {

	@Override
	public Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("sender_id"));
		}
		catch (NumberFormatException  e) {}
		if (id!=null) {
			List<Message> messages = serviceFactory.getMessageService().findMessagesBySenderId(id);
			System.out.println("ok");
			req.setAttribute("receivedMessages", messages);
		}
		return null;
	}
}
