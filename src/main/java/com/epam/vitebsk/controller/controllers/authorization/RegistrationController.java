package com.epam.vitebsk.controller.controllers.authorization;

import static com.epam.vitebsk.utils.StoredProperties.CONFIRM_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.DISPLAY_NAME_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.INFO_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.PASSWORD_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.SUCCESS_ATTRIBUTE;
import static com.epam.vitebsk.utils.StoredProperties.USERNAME_PARAMETR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.model.entity.User;
import com.epam.vitebsk.utils.Encrypter;
import com.epam.vitebsk.utils.Route;

public class RegistrationController implements Controller {

    private static final String EXCESS_DISPLAY_NAME_SYMBOLS = "Nickname should've less than 128 symbols!";
    private static final String EXCESS_PASSWORD_SYMBOLS = "Password should've less than 128 symbols!";
    private static final String LACK_PASSWORD_SYMBOLS = "Password should've at least 6 symbols!";
    private static final String EXCESS_USERNAME_SYMBOLS = "Email should've less than 256 symbols!";
    private static final String MISMATCH_PASSWORD_AND_CONFIRM = "Password and confirm password are mismatched!";
    private static final String SUCCESSFULL_REGISTRATION = "You have successfully registered!";

    private static final Integer MIN_PASSWORD_LENGTH = 6;
    private static final Integer MAX_PASSWORD_LENGTH = 128;
    private static final Integer MAX_DISPLAY_NAME_LENGTH = 128;
    private static final Integer MAX_USERNAME_LENGTH = 256;

    private HttpSession session;

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        String displayName = req.getParameter(DISPLAY_NAME_PARAMETR);
        String username = req.getParameter(USERNAME_PARAMETR);
        String password = req.getParameter(PASSWORD_PARAMETR);
        String confirm = req.getParameter(CONFIRM_PARAMETR);

        session = req.getSession();

        if (session != null) {
            session.removeAttribute("user");
        }

        if (displayName == null || username == null || password == null || confirm == null) {
            return null;
        }

        session = req.getSession();

        if (password.length() < MIN_PASSWORD_LENGTH) {
            return responseWithInfoMessage(Route.TO_REGISTRATION, LACK_PASSWORD_SYMBOLS);
        }

        if (password.length() > MAX_PASSWORD_LENGTH) {
            return responseWithInfoMessage(Route.TO_REGISTRATION, EXCESS_PASSWORD_SYMBOLS);
        }

        if (displayName.length() > MAX_DISPLAY_NAME_LENGTH) {
            return responseWithInfoMessage(Route.TO_REGISTRATION, EXCESS_DISPLAY_NAME_SYMBOLS);
        }

        if (username.length() > MAX_USERNAME_LENGTH) {
            return responseWithInfoMessage(Route.TO_REGISTRATION, EXCESS_USERNAME_SYMBOLS);
        }

        if (!password.equals(confirm)) {
            return responseWithInfoMessage(Route.TO_REGISTRATION, MISMATCH_PASSWORD_AND_CONFIRM);
        }

        String hashPassword = Encrypter.toHashPassword(password, username);

        User user = new User(null, username, hashPassword, displayName);

        serviceFactory.getUserService().save(user);

        session.setAttribute(SUCCESS_ATTRIBUTE, SUCCESSFULL_REGISTRATION);
        return responseWithInfoMessage(Route.TO_LOGIN, null);
    }

    private Response responseWithInfoMessage(String url, String message) {
        session.setAttribute(INFO_ATTRIBUTE, message);
        return new Response(url);
    }
}
