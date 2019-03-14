package com.epam.vitebsk.mvc.controller.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.MailService;
import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.service.ServiceFactory;
import com.epam.vitebsk.service.UserService;

public class MessageSendController extends MessageSupport implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        String username = req.getParameter("recipient");
        String subject = req.getParameter("subject");
        String msg = req.getParameter("message");

        if (username != null && subject != null && msg != null) {

            HttpSession session = null;

            Message message = buildMessage(req);

            if (subject.length() > 256) {
                session = req.getSession(false);
                session.setAttribute("message", message);
                return new Response("/message/edit.html?info=subject should've less than 256 symbols");
            }

            if (msg.length() > 1024) {
                session = req.getSession(false);
                session.setAttribute("message", message);
                return new Response("/message/edit.html?info=text of message should've less than 1024 symbols");
            }

            UserService userService = serviceFactory.getUserService();
            
            if (userService.findByUsername(username)==null) {
                session = req.getSession(false);
                session.setAttribute("message", message);
                return new Response("/message/edit.html?info=such user doesn't exists");
            }
            
            MessageService messageService = serviceFactory.getMessageService();     
                        
            messageService.save(message);
            MailService mailService = serviceFactory.getMailService();
            new Thread(() -> mailService.send(message)).start();
        }

        return new Response("/message/list.html");
    }
}
