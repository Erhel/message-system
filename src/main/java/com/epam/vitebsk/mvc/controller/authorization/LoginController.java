package com.epam.vitebsk.mvc.controller.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;
import com.epam.vitebsk.service.UserService;

public class LoginController implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            return null;
        }
                
        HttpSession session = req.getSession(false);
        
        if (session!=null) {
            session.invalidate();
            session = req.getSession(true);
        }

        if (password.length() < 6) {
            session.setAttribute("info", "password should've at least 6 symbols");
            return new Response("/authorization/login.html");
        }

        String hashPassword = Encrypter.toHashPassword(password, login);

        UserService service = serviceFactory.getUserService();

        User user = service.findByLoginAndPassword(login, hashPassword);

        if (user == null) {
            session.setAttribute("info", "incorrect password or login");
            return new Response("/authorization/login.html");
        }

        
        session.setAttribute("user", user);
        return new Response("/message/list.html");
    }

}
