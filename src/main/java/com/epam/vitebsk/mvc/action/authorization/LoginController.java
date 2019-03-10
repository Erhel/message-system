package com.epam.vitebsk.mvc.action.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class LoginController extends Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
        
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        
        if (login != null && password != null) {
            
            User user = serviceFactory.getUserService().findByLoginAndPassword(login, password);
            if (user != null) {

                HttpSession oldSession = req.getSession(false);
                
                if (oldSession!=null) {
                    oldSession.invalidate();
                }

                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                return new Response(true, "/message/list.html");
            } else {
                return new Response(true, "/authorization/login.html?message=incorrect password or login");
            }
        }        
        return null;
    }
    
    
}
