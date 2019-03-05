package com.epam.vitebsk.mvc.action.message;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Action;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MessageSaveAction extends Action {

    public Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        Boolean send = null;
        Long id = null;

        try {
            send = Boolean.parseBoolean(req.getParameter("send"));
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {}



        return new Response(true, "/message/view.html");
    }

    private Message buildMessage(HttpServletRequest req) {

        Long id = Long.parseLong(req.getParameter("id"));
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");
        String recipientUsername = req.getParameter("recipient");
        User recipient = new User(recipientUsername, null, null, null);
        //TODO: id for recipient
        HttpSession session = req.getSession(false);
        User sender = (User) session.getAttribute("user");
        return new Message(subject, message, sender, recipient, id);
    }
}
