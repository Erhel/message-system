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
        
        if (login != null && password != null) {
            
            if (password.length() < 6) {
                return new Response("/authorization/login.html?info=password should've at least 6 symbols");
            }
            
            String hashPassword = Encrypter.toHashPassword(password, login);
            
            UserService service = serviceFactory.getUserService();
            
            User user = service.findByLoginAndPassword(login, hashPassword);
            
            if (user != null) {

                HttpSession oldSession = req.getSession(false);
                
                if (oldSession!=null) {
                    oldSession.invalidate();
                }

                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                return new Response("/message/list.html");
            } else {
                return new Response("/authorization/login.html?info=incorrect password or login");
            }
        }        
        return null;
    }
    
    
}
