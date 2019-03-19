package com.epam.vitebsk.controller.controllers.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.utils.Route;

public class LogoutController implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new Response(Route.TO_LOGIN);
    }
}
