package com.epam.vitebsk.tests.model.dao.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.vitebsk.model.dao.jdbc.MessageJdbcDao;
import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.model.entity.User;

@RunWith(MockitoJUnitRunner.class)
public class MessageJdbcDaoTest extends Mockito {

    private static final Long USER1_ID = 1L;
    private static final String USERNAME1 = "andrey.koval@mail.ru";
    private static final String PASSWORD1 = "simplePassword";
    private static final String DISPLAY_NAME1 = "Andrey";

    private static final Long USER2_ID = 2L;
    private static final String USERNAME2 = "mike.lohan@mail.ru";
    private static final String PASSWORD2 = "hardPassword";
    private static final String DISPLAY_NAME2 = "Mike";

    private static final Long MESSAGE_ID = 1L;
    private static final String SUBJECT = "Greeting";
    private static final String TEXT = "Hello";

    private static final String INSERT = "insert into message values(default, ?, ?, ?, ?)";
    private static final String UPDATE = "update message set subject = ?, message = ?, sender_id = ?, recipient_id = ? where id = ?";
    private static final String DELETE = "delete from message where id = ?";
    private static final String SELECT_ONE = "select * from message where id = ?";
    private static final String SELECT_LIST_BY_SENDER = "select * from message where sender_id = ?";
    private static final String SELECT_LIST_BY_RECIPIENT = "select * from message where recipient_id = ?";

    private MessageJdbcDao messageJdbcDao;

    @Mock
    private Connection connection;

    private User sender;
    private User recipient;
    private List<Message> messages;
    private Message message;

    @Before
    public void setUp() {
        messageJdbcDao = spy(new MessageJdbcDao());
        messageJdbcDao.setConnection(connection);

        sender = new User(USER1_ID, USERNAME1, PASSWORD1, DISPLAY_NAME1);
        recipient = new User(USER2_ID, USERNAME2, PASSWORD2, DISPLAY_NAME2);

        message = new Message(MESSAGE_ID, SUBJECT, TEXT, sender, recipient);
        messages = new ArrayList<Message>(Arrays.nonNullElementsIn(Arrays.array(message)));
    }

    @Test
    public void createMessageTest() {
        doReturn(INSERT).when(messageJdbcDao).getSql(anyString());
        doNothing().when(messageJdbcDao).update(INSERT, SUBJECT, TEXT, USER1_ID, USER2_ID);

        messageJdbcDao.create(message);

        verify(messageJdbcDao, times(1)).getSql(anyString());
        verify(messageJdbcDao, times(1)).update(anyString(), anyString(), anyString(), anyLong(), anyLong());
    }

    @Test
    public void readMessageTest() {
        doReturn(SELECT_ONE).when(messageJdbcDao).getSql(anyString());
        doReturn(message).when(messageJdbcDao).selectOne(eq(SELECT_ONE), any(), eq(MESSAGE_ID));

        Message actual = messageJdbcDao.read(MESSAGE_ID);

        verify(messageJdbcDao, times(1)).getSql(anyString());
        verify(messageJdbcDao, times(1)).selectOne(anyString(), any(), anyLong());

        assertThat(actual).isEqualToComparingFieldByField(message);
    }

    @Test
    public void updateMessageTest() {
        doReturn(UPDATE).when(messageJdbcDao).getSql(anyString());
        doNothing().when(messageJdbcDao).update(UPDATE, SUBJECT, TEXT, USER1_ID, USER2_ID, MESSAGE_ID);

        messageJdbcDao.update(message);

        verify(messageJdbcDao, times(1)).getSql(anyString());
        verify(messageJdbcDao, times(1)).update(anyString(), anyString(), anyString(), anyLong(), anyLong(), anyLong());
    }

    @Test
    public void deleteMessageTest() {
        doReturn(DELETE).when(messageJdbcDao).getSql(anyString());
        doNothing().when(messageJdbcDao).update(DELETE, MESSAGE_ID);

        messageJdbcDao.delete(MESSAGE_ID);

        verify(messageJdbcDao, times(1)).getSql(anyString());
        verify(messageJdbcDao, times(1)).update(anyString(), anyLong());
    }

    @Test
    public void readBySenderIdTest() {
        doReturn(SELECT_LIST_BY_SENDER).when(messageJdbcDao).getSql(anyString());
        doReturn(messages).when(messageJdbcDao).selectList(eq(SELECT_LIST_BY_SENDER), any(), eq(USER1_ID));

        List<Message> actual = messageJdbcDao.readBySenderId(USER1_ID);

        verify(messageJdbcDao, times(1)).getSql(anyString());
        verify(messageJdbcDao, times(1)).selectList(anyString(), any(), anyLong());

        assertThat(actual).isEqualTo(messages);
    }

    @Test
    public void readByRecipientIdTest() {
        doReturn(SELECT_LIST_BY_RECIPIENT).when(messageJdbcDao).getSql(anyString());
        doReturn(messages).when(messageJdbcDao).selectList(eq(SELECT_LIST_BY_RECIPIENT), any(), eq(USER2_ID));

        List<Message> actual = messageJdbcDao.readByRecipientId(USER2_ID);

        verify(messageJdbcDao, times(1)).getSql(anyString());
        verify(messageJdbcDao, times(1)).selectList(anyString(), any(), anyLong());

        assertThat(actual).isEqualTo(messages);
    }
}
