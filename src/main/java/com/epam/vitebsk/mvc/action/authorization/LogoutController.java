package com.epam.vitebsk.mvc.action.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class LogoutController implements Controller {

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		HttpSession session = req.getSession(false);
		
		if (session!=null) {
			session.invalidate();
		}
		return new Response("/authorization/login.html");
	}
}
