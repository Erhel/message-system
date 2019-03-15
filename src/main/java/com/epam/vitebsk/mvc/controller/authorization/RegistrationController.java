package com.epam.vitebsk.mvc.controller.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;
import com.epam.vitebsk.service.UserService;

public class RegistrationController implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {

        String displayName = req.getParameter("displayName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");

        if (displayName == null || username == null || password == null || confirm == null) {
            return null;
        }
        
        
        
        HttpSession session = req.getSession(true);
        
        if (password.length() < 6) {
            session.setAttribute("info", "password should've at least 6 symbols");
            return new Response("/authorization/registration.html");
        }

        if (password.length() > 128) {
            session.setAttribute("info", "password should've less than 128 symbols");
            return new Response("/authorization/registration.html");
        }

        if (displayName.length() > 128) {
            session.setAttribute("info", "nickname should've less than 128 symbols");
            return new Response("/authorization/registration.html");
        }

        if (username.length() > 256) {
            session.setAttribute("info", "email should've less than 256 symbols");
            return new Response("/authorization/registration.html");
        }

        if (!password.equals(confirm)) {
            session.setAttribute("info", "password and confirm password are not matched");
            return new Response("/authorization/registration.html");
        }

        String hashPassword = Encrypter.toHashPassword(password, username);

        User user = new User(null, username, hashPassword, displayName);
        UserService service = serviceFactory.getUserService();
        service.save(user);

        session.setAttribute("success", "you are successfully registered");
        return new Response("/authorization/login.html");
    }

}
