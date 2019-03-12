package com.epam.vitebsk.mvc.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;

public abstract class MessageSupport {
    
    protected Message buildMessage(HttpServletRequest req) {

        Long id = null;       
        String subject = String.valueOf(req.getParameter("subject"));
        String message = String.valueOf(req.getParameter("message"));
        String recipientUsername = String.valueOf(req.getParameter("recipient"));
        User recipient = new User(null , recipientUsername, null, null);
        HttpSession session = req.getSession(false);
        User sender = (User) session.getAttribute("user");
        return new Message(id, subject, message, sender, recipient);
    }
}
