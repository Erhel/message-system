package com.epam.vitebsk.mvc.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Action;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageSaveAction extends Action {

    public Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        Boolean send = null;

        try {
            send = Boolean.parseBoolean(req.getParameter("send"));
        } catch (NumberFormatException e) {}

        Message message = buildMessage(req);
        
        MessageService messageService = serviceFactory.getMessageService();
        messageService.save(message);
        
        if (send) {
            messageService.send(message);
        }

        return new Response(true, "/message/list.html");
    }

    private Message buildMessage(HttpServletRequest req) {

        Long id = null;
        
        try {
            id = Long.parseLong(req.getParameter("id"));
        } 
        catch (NumberFormatException e) {}
        
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");
        String recipientUsername = req.getParameter("recipient");
        User recipient = new User(null , recipientUsername, null, null);
        //TODO: id for recipient
        HttpSession session = req.getSession(false);
        User sender = (User) session.getAttribute("user");
        return new Message(id, subject, message, sender, recipient);
    }
}
