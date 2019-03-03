package com.epam.vitebsk.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.service.ServiceFactory;

public abstract class Action {	
	public abstract Response perform(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory);
}
