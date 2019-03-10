package com.epam.vitebsk.mvc.action.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class RegistrationEditController extends Controller {

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		
		String displayName = req.getParameter("displayName");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if (displayName!= null && username!=null && password!=null) {
			
			
			
			return new Response(true, "/login.html?message=you are successfully registered");
		}
		
		return null;
	}
	
}
