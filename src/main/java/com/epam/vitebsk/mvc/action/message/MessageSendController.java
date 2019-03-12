package com.epam.vitebsk.mvc.action.message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.mvc.Controller;
import com.epam.vitebsk.mvc.Response;
import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.service.ServiceFactory;

public class MessageSendController extends MessageSupport implements Controller {

    @Override
    public Response handle(HttpServletRequest req, HttpServletResponse resp, ServiceFactory serviceFactory) {
        
        String username = String.valueOf(req.getParameter("recipient"));
        String subject = String.valueOf(req.getParameter("subject"));
        String msg = String.valueOf(req.getParameter("message"));
        
        if (username!=null && subject!=null && msg!=null) {
            
            Message message = buildMessage(req);
            
            if (subject.length() > 256) {
                req.setAttribute("message", message);
                req.setAttribute("error", "subject should've less than 256 symbols");
                return new Response(false, "/message/edit");
            }
            
            if (msg.length() > 1024) {
                req.setAttribute("message", message);
                req.setAttribute("error", "text of message should've less than 1024 symbols");
                return new Response(false, "/message/edit");
            }
            
            MessageService messageService = serviceFactory.getMessageService();
            messageService.save(message);            
        }
        
        return new Response("/message/list.html");
    }
}
