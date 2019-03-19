package com.epam.vitebsk.controller.service;

import com.epam.vitebsk.model.dao.DAOFactory;

public class ServiceFactory {

    private DAOFactory daoFactory;
    private MailService mailService;

    public ServiceFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public UserService getUserService() {

        UserService service = new UserService();

        service.setDao(daoFactory.getUserDao());

        return service;
    }

    public MessageService getMessageService() {

        MessageService service = new MessageService();

        service.setDao(daoFactory.getMessageDao());
        service.setUserDao(daoFactory.getUserDao());

        return service;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
