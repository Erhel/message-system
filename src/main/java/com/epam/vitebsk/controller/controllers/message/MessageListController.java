package com.epam.vitebsk.controller.controllers.message;

import static com.epam.vitebsk.utils.StoredProperties.INFO_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.RECEIVED_MESSAGES_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.SENT_MESSAGES_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.USER_ATTRIBUTE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.MessageService;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.model.entity.User;

public class MessageListController implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        HttpSession session = req.getSession(false);
        session.setAttribute(INFO_ATTRIBUTE, null);
        User user = (User) session.getAttribute(USER_ATTRIBUTE);

        MessageService service = serviceFactory.getMessageService();

        Long id = user.getId();

        List<Message> receivedMessages = service.findMessagesByRecipientId(id);
        List<Message> sentMessages = service.findMessagesBySenderId(id);

        req.setAttribute(RECEIVED_MESSAGES_ATTRIBUTE, receivedMessages);
        req.setAttribute(SENT_MESSAGES_ATTRIBUTE, sentMessages);

        return null;
    }
}
