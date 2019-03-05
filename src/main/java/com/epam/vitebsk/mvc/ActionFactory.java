package com.epam.vitebsk.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.epam.vitebsk.mvc.action.authorization.LoginAction;
import com.epam.vitebsk.mvc.action.message.MessageEditAction;
import com.epam.vitebsk.mvc.action.message.MessageListAction;
import com.epam.vitebsk.mvc.action.message.MessageSaveAction;

public class ActionFactory {
	
	private static Map<String, Class<? extends Action>> actions = new HashMap<>();
	
	static {
		actions.put("/login", LoginAction.class);
		actions.put("/message/list", MessageListAction.class);
		actions.put("/message/edit", MessageEditAction.class);
		actions.put("/message/save", MessageSaveAction.class);
	}
	
	public static Action getAction(String url) throws ServletException {
		
		Class<?> action = actions.get(url);
		
		try {
			return (Action) action.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ServletException(e);
		}
		
//		String [] arr = url.split("//");
//		System.out.println(url);
//		StringBuffer buffer = new StringBuffer();
//		for (String a : arr) {
//			a = a.substring(0, 1).toUpperCase() + a.substring(1);
//			buffer.append(a);
//		}
//		buffer.append("Action");
//		
//		return (Action) Class.forName(buffer.toString()).newInstance();
	}
}
