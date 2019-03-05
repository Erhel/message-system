package com.epam.vitebsk.service;

import com.epam.vitebsk.dao.DAOFactory;

public class ServiceFactory {

	private DAOFactory daoFactory;
	
	public UserService getUserService() {
	    
	    UserService service = new UserService();
	    
	    service.setDao(daoFactory.getUserDao());
	    
		return service;
	}
	
	public MessageService getMessageService() {
	    
	    MessageService service = new MessageService();
        
        service.setDao(daoFactory.getMessageDao());
        
        return service;
	}

	public ServiceFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
