package com.epam.vitebsk.mvc.action.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

public class RegistrationController implements Controller {

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		
		String displayName = String.valueOf(req.getParameter("displayName"));
		String username = String.valueOf(req.getParameter("username"));
		String password = String.valueOf(req.getParameter("password"));
		String confirm = String.valueOf(req.getParameter("confirm"));
		
		if (displayName!= null && username!=null && password!=null && confirm!=null) {
		    
		    if (password.length() < 6) {
		        return new Response("/authorization/registration.html/msg=password should've at least 6 symbols");
		    }
		    
		    if (password.length() > 128) {
		        return new Response("/authorization/registration.html/msg=password should've less than 128 symbols");
		    }
		    
		    if (displayName.length() > 128) {
		        return new Response("/authorization/registration.html/msg=nickname should've less than 128 symbols");
		    }
		    
		    if (username.length() > 256) {
                return new Response("/authorization/registration.html/msg=email should've less than 256 symbols");
            }
		    
		    if (!password.equals(confirm)) {
		        return new Response("/authorization/registration.html?msg=password and confirm password are not matched");
		    }
			
			User user = new User(null, username, password, displayName);
			serviceFactory.getUserService().save(user);
			
			return new Response("/authorization/login.html?msg=you are successfully registered");
		}
		
		return null;
	}
	
}
