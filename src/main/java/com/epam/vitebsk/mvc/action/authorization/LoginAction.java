package com.epam.vitebsk.mvc.action.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Action;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class LoginAction extends Action {

    @Override
    public Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && password != null) {



            serviceFactory.getUserService().createSchema();
            
            User andrey = new User(null, "andrey.koval@mail.ru", "simple", "Andrey");
            User mike = new User(null, "mike.lohan@mail.ru", "hard", "Mike");
            
            serviceFactory.getUserService().save(mike);
            serviceFactory.getUserService().save(andrey);
            
//            Message m1 = new Message(null, "hello", "it's me", andrey, mike);
//            Message m2 = new Message(null, "hello too", "i reply", mike, andrey);
//
//            serviceFactory.getMessageService().save(m1);
//            serviceFactory.getMessageService().save(m2);
//            User user = serviceFactory.getUserService().findByLoginAndPassword(login, password);
            User user = serviceFactory.getUserService().find(1L);
            if (user != null) {

                HttpSession oldSession = req.getSession(false);

                if (oldSession!=null) {
                    oldSession.invalidate();
                }

                HttpSession session = req.getSession(true);
                session.setAttribute("user", user);
                return new Response(true, "/message/list.html");
            } else {
                return new Response(true, "/notFound.html");
            }
        }
        return null;
    }
    
    
}
