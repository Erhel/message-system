package com.epam.vitebsk.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.service.ServiceFactory;

public interface Controller {	
	public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory);
}
