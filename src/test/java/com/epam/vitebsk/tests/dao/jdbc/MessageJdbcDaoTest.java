package com.epam.vitebsk.tests.dao.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Ignore;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.epam.vitebsk.dao.Mapper;
import com.epam.vitebsk.dao.jdbc.MessageJdbcDao;
import com.epam.vitebsk.entity.Message;
import com.epam.vitebsk.entity.User;

public class MessageJdbcDaoTest extends Mockito {

    private MessageJdbcDao messageJdbcDao;
    private User sender = new User(1L, "andrey.koval@mail.ru", "simple", "Andrey");
    private User recipient = new User(2L, "mike.lohan@mail.ru", "hardPassword", "Mike");
    private List<Message> messages;
    private Message message;
    private String insert;
    private String selectOne;
    private String selectList;
    private MessageJdbcDao messageJdbcDaoSpy;

    @Mock
    Connection connection;

    @Mock
    Mapper<Message> mapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        messageJdbcDao = new MessageJdbcDao();
        messageJdbcDaoSpy = spy(messageJdbcDao);
        messageJdbcDao.setConnection(connection);
        message =  new Message(1L, "test", "testMessage", sender, recipient);
        messages = new ArrayList<Message>(Arrays.nonNullElementsIn(Arrays.array(message)));
        insert = "insert into message values(default, ?, ?, ?, ?)";
        selectOne = "select * from message where id = ?";
        selectList = "select * from message";
    }

    @Ignore
    public void test1() {
        
        doNothing().when(messageJdbcDaoSpy).update(insert, message.getSubject(), message.getMessage(), sender.getId(), recipient.getId());
        when(mapper.map(any())).thenReturn(message);
        when(messageJdbcDao.selectOne(selectOne, mapper, message.getId())).thenReturn(message);

        messageJdbcDao.create(message);
        messageJdbcDao.delete(message.getId());
        Message msg = messageJdbcDao.read(message.getId());
        messageJdbcDao.update(message);

        assertThat(msg).isEqualToComparingFieldByField(message);
        
        verify(messageJdbcDaoSpy, times(3)).update(anyString(), anyVararg());
        verify(messageJdbcDaoSpy, times(1)).selectOne(anyString(), any(), anyVararg());
                
    }
    
    @Ignore
    public void test2() {
        
        when(messageJdbcDaoSpy.selectList(selectList, mapper, 1L)).thenReturn(messages);
        
        List<Message> received = messageJdbcDao.readBySenderId(sender.getId());
        List<Message> sent = messageJdbcDao.readByRecipientId(recipient.getId()); 
        
        assertThat(received.equals(messages)).isTrue();
        assertThat(sent.equals(messages)).isTrue();
    }

}
