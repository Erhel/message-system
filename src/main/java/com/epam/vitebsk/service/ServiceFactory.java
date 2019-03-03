package com.epam.vitebsk.service;

import java.util.List;

import com.epam.vitebsk.dao.DAOFactory;
import com.epam.vitebsk.entity.Message;

public class ServiceFactory {

	private DAOFactory daoFactory;
	
	public LoginService getLoginService() {
		return new LoginService();
	}
	
	public MessageService getMessageService() {
		return new MessageService();
	}

	public ServiceFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
