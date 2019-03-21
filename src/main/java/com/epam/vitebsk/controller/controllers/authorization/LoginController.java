package com.epam.vitebsk.controller.controllers.authorization;

import static com.epam.vitebsk.utils.StoredProperties.INFO_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.PASSWORD_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.USERNAME_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.USER_ATTRIBUTE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.controller.service.UserService;
import com.epam.vitebsk.model.entity.User;
import com.epam.vitebsk.utils.Encrypter;
import com.epam.vitebsk.utils.Route;

public class LoginController implements Controller {

    private static final String LACK_PASSWORD_SYMBOLS = "Password should've at least 6 symbols!";
    private static final String INCORRECT_CREDENTIALS = "Incorrect login or password!";
    private static final String INCORRECT_LOGIN = "Incorrect login";

    private static final Integer MIN_PASSWORD_LENGTH = 6;

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        String username = req.getParameter(USERNAME_PARAMETR);
        String password = req.getParameter(PASSWORD_PARAMETR);
        HttpSession session = null;

        if (username == null || password == null) {
            return null;
        }

        session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        session = req.getSession();

        if (!username.matches("[-._a-zA-Z]{1,}@\\w+\\.\\w+")) {
            session.setAttribute(INFO_ATTRIBUTE, INCORRECT_LOGIN);
            return new Response(Route.TO_LOGIN);
        }
        
        if (password.length() < MIN_PASSWORD_LENGTH) {
            session.setAttribute(INFO_ATTRIBUTE, LACK_PASSWORD_SYMBOLS);
            return new Response(Route.TO_LOGIN);
        }

        String hashPassword = Encrypter.toHashPassword(password, username);

        UserService service = serviceFactory.getUserService();

        User user = service.findByLoginAndPassword(username, hashPassword);

        if (user == null) {
            session.setAttribute(INFO_ATTRIBUTE, INCORRECT_CREDENTIALS);
            return new Response(Route.TO_LOGIN);
        }

        session.setAttribute(USER_ATTRIBUTE, user);
        return new Response(Route.TO_MESSAGE_LIST);
    }
}
