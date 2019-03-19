package com.epam.vitebsk.controller.controllers.message;

import static com.epam.vitebsk.utils.StoredProperties.MESSAGE_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.RECIPIENT_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.SUBJECT_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.USER_ATTRIBUTE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.model.entity.User;

public abstract class MessageSupport {

    protected Message buildMessage(HttpServletRequest req) {
        Long id = null;

        String subject = req.getParameter(SUBJECT_PARAMETR);
        String message = req.getParameter(MESSAGE_PARAMETR);
        String recipientUsername = req.getParameter(RECIPIENT_PARAMETR);
        HttpSession session = req.getSession(false);

        User recipient = new User(null, recipientUsername, null, null);
        User sender = (User) session.getAttribute(USER_ATTRIBUTE);

        return new Message(id, subject, message, sender, recipient);
    }
}
