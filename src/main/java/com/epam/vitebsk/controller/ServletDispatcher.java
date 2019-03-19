package com.epam.vitebsk.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.controller.controllers.Controller;
import com.epam.vitebsk.controller.controllers.ControllerFactory;
import com.epam.vitebsk.controller.service.MailService;
import com.epam.vitebsk.controller.service.ServiceFactory;
import com.epam.vitebsk.model.dao.DAOFactory;
import com.epam.vitebsk.model.pool.ConnectionPool;
import com.epam.vitebsk.utils.Resource;

public class ServletDispatcher extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String HTML_EXTENSION = ".html";
    private static final String JSP_EXTENSION = ".jsp";
    private static final String JSP_FOLDER = "/WEB-INF/jsp";

    private static final String CONNECTION_PROPERTIES = "/connection.properties";

    private ConnectionPool connectionPool;

    private ServiceFactory serviceFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        Map<String, String> map = new Resource(CONNECTION_PROPERTIES).load().toMap();

        try {
            connectionPool = new ConnectionPool(map);
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        DAOFactory daoFactory = new DAOFactory(connectionPool);

        serviceFactory = new ServiceFactory(daoFactory);

        MailService mailService = new MailService("localhost", "25", "message-service@company.com", "password");
        serviceFactory.setMailService(mailService);
    }

    @Override
    public void destroy() {
        connectionPool.destroy();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String context = req.getContextPath();
        int index = url.lastIndexOf(HTML_EXTENSION);
        url = url.substring(context.length(), index);

        Controller controller = getController(url);

        Response response = null;
        if (controller != null) {
            response = controller.handle(req, resp, serviceFactory);
        }

        if (response != null) {
            resp.sendRedirect(context + response.getUrl() + HTML_EXTENSION);
        } else {
            req.getRequestDispatcher(JSP_FOLDER + url + JSP_EXTENSION).forward(req, resp);
        }
    }

    public Controller getController(String url) throws ServletException {
        return ControllerFactory.getController(url);
    }
}
