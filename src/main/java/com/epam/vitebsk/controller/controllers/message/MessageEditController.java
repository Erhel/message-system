package com.epam.vitebsk.controller.controllers.message;

import static com.epam.vitebsk.utils.StoredProperties.ID_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.MESSAGE_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.USERNAMES_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.USER_ATTRIBUTE;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.MessageService;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.model.entity.User;
import com.epam.vitebsk.utils.Route;

public class MessageEditController implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        Long id = null;
        Message message = null;

        try {
            id = Long.parseLong(req.getParameter(ID_PARAMETR));
        } catch (NumberFormatException e) {
            // TODO: Logging
        }

        MessageService messageService = serviceFactory.getMessageService();

        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute(USER_ATTRIBUTE);

        Set<String> usernames = messageService.findUsernamesByUserId(currentUser.getId());

        req.setAttribute(USERNAMES_ATTRIBUTE, usernames);

        if (id != null) {
            message = messageService.find(id);

            User sender = message.getSender();
            User recipient = message.getRecipient();

            if (sender.getId() != currentUser.getId() && recipient.getId() != currentUser.getId()) {
                return new Response(Route.TO_MESSAGE_LIST);
            }

            req.setAttribute(MESSAGE_ATTRIBUTE, message);
        } else {
            message = (Message) session.getAttribute(MESSAGE_ATTRIBUTE);
            if (message != null) {
                session.setAttribute(MESSAGE_ATTRIBUTE, null);
                req.setAttribute(MESSAGE_ATTRIBUTE, message);
            }
        }

        return null;
    }
}
