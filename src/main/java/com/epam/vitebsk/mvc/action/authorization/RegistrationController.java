package com.epam.vitebsk.mvc.action.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class RegistrationController extends Controller {

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		
		String displayName = req.getParameter("displayName");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String confirm = req.getParameter("confirm");
		
		if (displayName!= null && username!=null && password!=null && confirm!=null) {
		    
		    if (!password.equals(confirm)) {
		        return new Response(true, "/authorization/registration.html?message=password and confirm password are not matched");
		    }
			
			User user = new User(null, username, password, displayName);
			serviceFactory.getUserService().save(user);
			
			return new Response(true, "/authorization/login.html?message=you are successfully registered");
		}
		
		return null;
	}
	
}
