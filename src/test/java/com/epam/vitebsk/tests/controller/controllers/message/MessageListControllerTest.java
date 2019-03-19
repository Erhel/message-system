package com.epam.vitebsk.tests.controller.controllers.message;

import static com.epam.vitebsk.utils.StoredProperties.USER_ATTRIBUTE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.message.MessageListController;
import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.model.entity.User;

public class MessageListControllerTest extends MessageTestSupport {

    private User user;
    private List<Message> receivedMessages;
    private List<Message> sentMessages;

    @Before
    public void setUp() {
        super.setUp();
        controller = new MessageListController();
        user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
        receivedMessages = new ArrayList<Message>();
        sentMessages = new ArrayList<Message>();
    }

    @Test
    public void listMessagesTest() {
        when(session.getAttribute(USER_ATTRIBUTE)).thenReturn(user);
        when(messageService.findMessagesByRecipientId(USER_ID)).thenReturn(receivedMessages);
        when(messageService.findMessagesBySenderId(USER_ID)).thenReturn(sentMessages);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(serviceFactory, times(1)).getMessageService();
        verify(req, times(2)).setAttribute(anyString(), anyCollectionOf(ArrayList.class));

        assertThat(response).isNull();

    }
}
