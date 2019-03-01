package com.epam.vitebsk.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.User;
import com.epam.vitebsk.service.LoginService;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
//        try {
//            ConnectionPool connectionPool = new ConnectionPool(10, 100, "jdbc:derby:D:\\derby\\message-system",
//                    "derby", "derby");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
    
    public LoginService getLoginService() {
        return new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String method = req.getMethod();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (method == "POST") {
            LoginService service = getLoginService();
            User user = service.findByLoginAndPassword(login, password);
            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                resp.sendRedirect("/index.html");
            } 
            else {
                resp.sendRedirect("/userNotFound.html");
            }
        }
    }
}
