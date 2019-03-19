package com.epam.vitebsk.utils;

import java.util.ArrayList;
import java.util.List;

public final class Route {

    public static final String TO_LOGIN = "/authorization/login";
    public static final String TO_LOGOUT = "/authorization/logout";
    public static final String TO_REGISTRATION = "/authorization/registration";
    public static final String TO_MESSAGE_LIST = "/message/list";
    public static final String TO_MESSAGE_EDIT = "/message/edit";
    public static final String TO_MESSAGE_SEND = "/message/send";

    public static List<String> getRoutes() {
        List<String> routes = new ArrayList<>();
        routes.add(TO_LOGIN);
        routes.add(TO_LOGOUT);
        routes.add(TO_REGISTRATION);
        routes.add(TO_MESSAGE_EDIT);
        routes.add(TO_MESSAGE_LIST);
        routes.add(TO_MESSAGE_SEND);
        return routes;
    }

    private Route() {
    }
}
