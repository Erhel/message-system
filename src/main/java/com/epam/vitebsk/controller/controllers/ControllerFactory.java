package com.epam.vitebsk.controller.controllers;

import java.util.HashMap;
import java.util.Map;

import com.epam.vitebsk.controller.controllers.authorization.LoginController;
import com.epam.vitebsk.controller.controllers.authorization.LogoutController;
import com.epam.vitebsk.controller.controllers.authorization.RegistrationController;
import com.epam.vitebsk.controller.controllers.message.MessageEditController;
import com.epam.vitebsk.controller.controllers.message.MessageListController;
import com.epam.vitebsk.controller.controllers.message.MessageSendController;
import com.epam.vitebsk.controller.exception.ControllerInstantiationException;
import com.epam.vitebsk.utils.Route;

public class ControllerFactory {

    private static final String UNABLE_CREATE_CONTROLLER = "Unable instantiate controller";

    private static Map<String, Class<? extends Controller>> controllers = new HashMap<>();

    static {
        controllers.put(Route.TO_LOGIN, LoginController.class);
        controllers.put(Route.TO_LOGOUT, LogoutController.class);
        controllers.put(Route.TO_REGISTRATION, RegistrationController.class);
        controllers.put(Route.TO_MESSAGE_LIST, MessageListController.class);
        controllers.put(Route.TO_MESSAGE_EDIT, MessageEditController.class);
        controllers.put(Route.TO_MESSAGE_SEND, MessageSendController.class);
    }

    public static Controller getController(String url) throws ControllerInstantiationException {

        Class<?> controller = controllers.get(url);

        try {
            return instasntiateController(controller);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ControllerInstantiationException(UNABLE_CREATE_CONTROLLER, e);
        }
    }

    public static Controller instasntiateController(Class<?> controller)
            throws InstantiationException, IllegalAccessException {
        return (Controller) controller.newInstance();
    }
}
