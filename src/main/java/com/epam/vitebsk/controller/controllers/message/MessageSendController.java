package com.epam.vitebsk.controller.controllers.message;

import static com.epam.vitebsk.utils.StoredProperties.INFO_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.MESSAGE_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.MESSAGE_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.RECIPIENT_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.SUBJECT_PARAMETR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.MailService;
import com.epam.vitebsk.controller.service.MessageService;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.controller.service.UserService;
import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.utils.Route;

public class MessageSendController extends MessageSupport implements Controller {

    private static final String EXCESS_SUBJECT_SYMBOLS = "Subject should've less than 256 symbols!";
    private static final String EXCESS_MESSAGE_SYMBOLS = "Text of message should've less than 1024 symbols!";
    private static final String USER_NOT_EXIST = "Such user doesn't exists!";

    private static final Integer MAX_SUBJECT_LENGTH = 256;
    private static final Integer MAX_MESSAGE_LENGTH = 1024;

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        String username = req.getParameter(RECIPIENT_PARAMETR);
        String subject = req.getParameter(SUBJECT_PARAMETR);
        String msg = req.getParameter(MESSAGE_PARAMETR);

        HttpSession session = req.getSession(false);

        if (username != null && subject != null && msg != null) {

            Message message = buildMessage(req);

            if (subject.length() > MAX_SUBJECT_LENGTH) {
                session.setAttribute(MESSAGE_ATTRIBUTE, message);
                session.setAttribute(INFO_ATTRIBUTE, EXCESS_SUBJECT_SYMBOLS);
                return new Response(Route.TO_MESSAGE_EDIT);
            }

            if (msg.length() > MAX_MESSAGE_LENGTH) {
                session.setAttribute(MESSAGE_ATTRIBUTE, message);
                session.setAttribute(INFO_ATTRIBUTE, EXCESS_MESSAGE_SYMBOLS);
                return new Response(Route.TO_MESSAGE_EDIT);
            }

            UserService userService = serviceFactory.getUserService();

            if (userService.findByUsername(username) == null) {
                session.setAttribute(MESSAGE_ATTRIBUTE, message);
                session.setAttribute(INFO_ATTRIBUTE, USER_NOT_EXIST);
                return new Response(Route.TO_MESSAGE_EDIT);
            }

            MessageService messageService = serviceFactory.getMessageService();

            messageService.save(message);
            MailService mailService = serviceFactory.getMailService();
            new Thread(() -> mailService.send(message)).start();
        }

        return new Response(Route.TO_MESSAGE_LIST);
    }
}
