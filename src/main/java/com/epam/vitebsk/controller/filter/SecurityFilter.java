package com.epam.vitebsk.controller.filter;

import static com.epam.vitebsk.utils.StoredProperties.USER_ATTRIBUTE;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.model.entity.User;
import com.epam.vitebsk.utils.Route;

public class SecurityFilter implements Filter {

    private static final String HTML_EXTENSION = ".html";

    private static List<String> routes = Route.getRoutes();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String context = req.getContextPath();
        String url = req.getRequestURI();
        int index = url.lastIndexOf(HTML_EXTENSION);
        url = url.substring(context.length(), index);

        HttpSession session = req.getSession(false);

        if (session != null) {
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (user != null) {
                if (routes.contains(url)) {
                    chain.doFilter(req, resp);
                } else {
                    resp.sendRedirect(context + Route.TO_MESSAGE_LIST + HTML_EXTENSION);
                }
                return;
            }
        }

        if (url.intern() == Route.TO_LOGIN || url.intern() == Route.TO_REGISTRATION) {
            chain.doFilter(req, resp);
            return;
        }

        resp.sendRedirect(context + Route.TO_LOGIN + HTML_EXTENSION);
    }

    @Override
    public void destroy() {

    }

}