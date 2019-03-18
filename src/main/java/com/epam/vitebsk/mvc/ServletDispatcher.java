package com.epam.vitebsk.mvc;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.dao.DAOFactory;
import com.epam.vitebsk.dao.jdbc.pool.ConnectionPool;
import com.epam.vitebsk.service.MailService;
import com.epam.vitebsk.service.ServiceFactory;

public class ServletDispatcher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ConnectionPool connectionPool;

	private ServiceFactory serviceFactory;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			connectionPool = new ConnectionPool(100, "jdbc:postgresql://localhost:5439/message-system", "postgres", "postgres", "org.postgresql.Driver");
		} catch (SQLException | ClassNotFoundException e) {
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
		int index = url.lastIndexOf(".html");
		url = url.substring(context.length(), index);

		Controller controller = getController(url);
		
		Response response = null;
		if (controller != null) {
			response = controller.handle(req, resp, serviceFactory);
		}
		
		if (response!=null) {
			resp.sendRedirect(context + response.getUrl());
		} else {
			req.getRequestDispatcher("/WEB-INF/jsp" + url + ".jsp").forward(req, resp);
		}
	}
	
	public Controller getController(String url) throws ServletException {
		return ControllerFactory.getController(url);
	}
}
