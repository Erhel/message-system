package com.epam.vitebsk.tests.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.dao.DAOFactory;
import com.epam.vitebsk.dao.MessageDao;
import com.epam.vitebsk.dao.UserDao;
import com.epam.vitebsk.service.MailService;
import com.epam.vitebsk.service.MessageService;
import com.epam.vitebsk.service.ServiceFactory;
import com.epam.vitebsk.service.UserService;

public class ServiceFactoryTest extends Mockito {

	private ServiceFactory serviceFactory;
	
	@Mock
	private DAOFactory daoFactory;
	
	@Mock
	private MailService mailService;
	
	@Mock
	UserDao userDao;
	
	@Mock
	MessageDao messageDao;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		serviceFactory = new ServiceFactory(daoFactory);
		serviceFactory.setMailService(mailService);
	}

	@Test
	public void getUserServiceTest() {
		when(daoFactory.getUserDao()).thenReturn(userDao);
		
		UserService actual = serviceFactory.getUserService();
		
		verify(daoFactory, times(1)).getUserDao();
		
		assertThat(actual).isNotNull();
	}
	
	@Test
	public void getMessageServiceTest() {
		when(daoFactory.getMessageDao()).thenReturn(messageDao);
		
		MessageService actual = serviceFactory.getMessageService();
		
		verify(daoFactory, times(1)).getMessageDao();
		
		assertThat(actual).isNotNull();
	}
	
	@Test
	public void getMailServiceTest() {
		MailService actual = serviceFactory.getMailService();
		
		assertThat(actual).isEqualTo(mailService);
	}

}
