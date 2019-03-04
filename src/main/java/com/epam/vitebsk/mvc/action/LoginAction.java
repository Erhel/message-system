package com.epam.vitebsk.mvc.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Action;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.LoginService;
import com.epam.vitebsk.service.Service;
import com.epam.vitebsk.service.ServiceFactory;

public class LoginAction extends Action {

	@Override
	public Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if (login != null && password != null) {
            User user = serviceFactory.getLoginService().findByLoginAndPassword(login, password);
			if (user != null) {
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
