package com.epam.vitebsk.controller.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.epam.vitebsk.controller.controllers.authorization.LoginController;
import com.epam.vitebsk.controller.controllers.authorization.LogoutController;
import com.epam.vitebsk.controller.controllers.authorization.RegistrationController;
import com.epam.vitebsk.controller.controllers.message.MessageEditController;
import com.epam.vitebsk.controller.controllers.message.MessageListController;
import com.epam.vitebsk.controller.controllers.message.MessageSendController;
import com.epam.vitebsk.utils.Route;

public class ControllerFactory {

    private static Map<String, Class<? extends Controller>> controllers = new HashMap<>();

    static {
        controllers.put(Route.TO_LOGIN, LoginController.class);
        controllers.put(Route.TO_LOGOUT, LogoutController.class);
        controllers.put(Route.TO_REGISTRATION, RegistrationController.class);
        controllers.put(Route.TO_MESSAGE_LIST, MessageListController.class);
        controllers.put(Route.TO_MESSAGE_EDIT, MessageEditController.class);
        controllers.put(Route.TO_MESSAGE_SEND, MessageSendController.class);
    }

    public static Controller getController(String url) throws ServletException {

        Class<?> controller = controllers.get(url);

        try {
            return instasntiateController(controller);
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO: exception
            throw new ServletException("Such URI doesn't exist.", e);
        }
    }

    public static Controller instasntiateController(Class<?> controller)
            throws InstantiationException, IllegalAccessException {
        return (Controller) controller.newInstance();
    }
}
