package com.epam.vitebsk.mvc.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageSaveController extends MessageSupport implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        String username = req.getParameter("recipient");
        String subject = req.getParameter("subject");
        String msg = req.getParameter("message");

        if (username != null && subject != null && msg != null) {

            HttpSession session = null;

            Message message = buildMessage(req);

            if (!username.matches(".*@.*")) {
                return new Response("/message/edit.html?msg=wrong email");
            }
            
            if (subject.length() > 256) {
                session = req.getSession(false);
                session.setAttribute("message", message);
                return new Response("/message/edit.html?msg=subject should've less than 256 symbols");
            }

            if (msg.length() > 1024) {
                session = req.getSession(false);
                session.setAttribute("message", message);
                return new Response("/message/edit.html?msg=text of message should've less than 1024 symbols");
            }

            MessageService messageService = serviceFactory.getMessageService();
            messageService.save(message);
        }

        return new Response("/message/list.html");
    }
}
