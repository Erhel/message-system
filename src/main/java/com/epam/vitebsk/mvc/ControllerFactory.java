package com.epam.vitebsk.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.epam.vitebsk.mvc.controller.authorization.LoginController;
import com.epam.vitebsk.mvc.controller.authorization.LogoutController;
import com.epam.vitebsk.mvc.controller.authorization.RegistrationController;
import com.epam.vitebsk.mvc.controller.message.MessageEditController;
import com.epam.vitebsk.mvc.controller.message.MessageListController;
import com.epam.vitebsk.mvc.controller.message.MessageSendController;

public class ControllerFactory {
	
	private static Map<String, Class<? extends Controller>> controllers = new HashMap<>();
	
	static {
		controllers.put("/authorization/login", LoginController.class);
		controllers.put("/authorization/logout", LogoutController.class);
		controllers.put("/authorization/registration", RegistrationController.class);
		controllers.put("/message/list", MessageListController.class);
		controllers.put("/message/edit", MessageEditController.class);
		controllers.put("/message/send", MessageSendController.class);
	}
	
	public static Controller getController(String url) throws ServletException {
		
		Class<?> controller = controllers.get(url);
		
		try {
			return (Controller) controller.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ServletException(e);
		}
	}
}
