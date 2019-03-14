package com.epam.vitebsk.mvc.controller.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.ServiceFactory;

<<<<<<< HEAD
public class RegistrationController extends Encrypter implements Controller {
=======
public class RegistrationController extends Decrypter implements Controller {
>>>>>>> fca562845777d22a8aee753d6755f6781d0cf618

	@Override
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
		
		String displayName = req.getParameter("displayName");		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String confirm = req.getParameter("confirm");
		
		if (displayName!= null && username!=null && password!=null && confirm!=null) {
		    
		    if (password.length() < 6) {
		        return new Response("/authorization/registration.html?info=password should've at least 6 symbols");
		    }
		    
		    if (password.length() > 128) {
		        return new Response("/authorization/registration.html?info=password should've less than 128 symbols");
		    }
		    
		    if (displayName.length() > 128) {
		        return new Response("/authorization/registration.html?info=nickname should've less than 128 symbols");
		    }
		    
		    if (username.length() > 256) {
                return new Response("/authorization/registration.html?info=email should've less than 256 symbols");
            }
		    
		    if (!password.equals(confirm)) {
		        return new Response("/authorization/registration.html?info=password and confirm password are not matched");
		    }
			
<<<<<<< HEAD
		    String hashPassword = Encrypter.toHashPassword(password, username);
=======
		    String hashPassword = Decrypter.toHashPassword(password, username);
>>>>>>> fca562845777d22a8aee753d6755f6781d0cf618
		    
			User user = new User(null, username, hashPassword, displayName);
			serviceFactory.getUserService().save(user);
			
			return new Response("/authorization/login.html?success=you are successfully registered");
		}
		
		return null;
	}
	
}
