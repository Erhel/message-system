package com.epam.vitebsk.tests.controller.controllers.message;

import static com.epam.vitebsk.utils.StoredProperties.MESSAGE_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.RECIPIENT_PARAMETR;
import static com.epam.vitebsk.utils.StoredProperties.SUBJECT_PARAMETR;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.epam.vitebsk.controller.Response;
import com.epam.vitebsk.controller.controllers.message.MessageSendController;
import com.epam.vitebsk.controller.service.MailService;
import com.epam.vitebsk.controller.service.UserService;
import com.epam.vitebsk.model.entity.Message;
import com.epam.vitebsk.model.entity.User;
import com.epam.vitebsk.utils.Route;

public class MessageSendControllerTest extends MessageTestSupport {
    private static String LARGE_SUBJECT = RandomString.make(257);
    private static String LARGE_TEXT = RandomString.make(1025);

    private User user;
    private User user2;
    private Message message;
    private Response responseToListPage;
    private Response responseToEditPage;

    @Mock
    UserService userService;

    @Mock
    MailService mailService;

    @Before
    public void setUp() {
        super.setUp();
        controller = new MessageSendController();
        user = new User(USER_ID, USERNAME, PASSWORD, DISPLAY_NAME);
        user2 = new User(USER2_ID, USERNAME2, "", "");
        message = new Message(MESSAGE_ID, SUBJECT, TEXT, user, user2);
        responseToListPage = new Response(Route.TO_MESSAGE_LIST);
        responseToEditPage = new Response(Route.TO_MESSAGE_EDIT);

        when(req.getParameter(RECIPIENT_PARAMETR)).thenReturn(USERNAME2);
        when(req.getParameter(SUBJECT_PARAMETR)).thenReturn(SUBJECT);
        when(req.getParameter(MESSAGE_PARAMETR)).thenReturn(TEXT);
    }

    @Test
    public void redirectToListPageWhenRecipientNullTest() {
        doReturn(null).when(req).getParameter(RECIPIENT_PARAMETR);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(messageService, never()).save(any(Message.class));
        verify(mailService, never()).send(any(Message.class));

        assertThat(response).isEqualToComparingFieldByField(responseToListPage);
    }

    @Test
    public void redirectToListPageWhenSubjectNullTest() {
        doReturn(null).when(req).getParameter(SUBJECT_PARAMETR);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(messageService, never()).save(any(Message.class));
        verify(mailService, never()).send(any(Message.class));

        assertThat(response).isEqualToComparingFieldByField(responseToListPage);
    }

    @Test
    public void redirectToListPageWhenTextNullTest() {
        doReturn(null).when(req).getParameter(MESSAGE_PARAMETR);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(messageService, never()).save(any(Message.class));
        verify(mailService, never()).send(any(Message.class));

        assertThat(response).isEqualToComparingFieldByField(responseToListPage);
    }

    @Test
    public void largeSubjectTest() {
        doReturn(LARGE_SUBJECT).when(req).getParameter(SUBJECT_PARAMETR);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(session, times(2)).setAttribute(anyString(), any());
        verify(messageService, never()).save(any(Message.class));
        verify(mailService, never()).send(any(Message.class));

        assertThat(response).isEqualToComparingFieldByField(responseToEditPage);
    }

    @Test
    public void largeTextTest() {
        doReturn(LARGE_TEXT).when(req).getParameter(MESSAGE_PARAMETR);
        when(serviceFactory.getUserService()).thenReturn(userService);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(session, times(2)).setAttribute(anyString(), any());
        verify(messageService, never()).save(any(Message.class));
        verify(mailService, never()).send(any(Message.class));

        assertThat(response).isEqualToComparingFieldByField(responseToEditPage);
    }

    @Test
    public void recipientNotExistTest() {
        when(serviceFactory.getUserService()).thenReturn(userService);
        when(userService.findByUsername(anyString())).thenReturn(null);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(userService, times(1)).findByUsername(anyString());
        verify(session, times(2)).setAttribute(anyString(), any());
        verify(messageService, never()).save(any(Message.class));
        verify(mailService, never()).send(any(Message.class));

        assertThat(response).isEqualToComparingFieldByField(responseToEditPage);
    }

    @Test
    public void successfullSentMessageTest() {
        when(serviceFactory.getUserService()).thenReturn(userService);
        when(serviceFactory.getMailService()).thenReturn(mailService);
        when(userService.findByUsername(anyString())).thenReturn(user2);

        Response response = controller.handle(req, resp, serviceFactory);

        verify(messageService, times(1)).save(any(Message.class));
        verify(serviceFactory, times(1)).getMailService();

        assertThat(response).isEqualToComparingFieldByField(responseToListPage);
    }
}
