package com.epam.vitebsk.mvc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.vitebsk.entity.User;

public class SecurityFilter implements Filter {

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
        url = url.substring(context.length());
        if (url.intern() == "/authorization/login.html" || url.intern()=="/authorization/registration.html") {
            chain.doFilter(req, resp);
            return;
        }
        
        HttpSession session = req.getSession(false);

        User user = null;
        
        if (session!=null) {
            user = (User) session.getAttribute("user");
            
            if (user!= null) {
                chain.doFilter(req, resp);
                return;
            }
        }
        resp.sendRedirect(context + "/authorization/login.html");
    }

    @Override
    public void destroy() {

    }

}