package com.epam.vitebsk.controller.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.service.ServiceFactory;

public interface Controller {
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory);
}
