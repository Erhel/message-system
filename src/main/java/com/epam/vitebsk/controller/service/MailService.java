package com.epam.vitebsk.controller.service;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.epam.vitebsk.controller.exception.SendMessageException;
import com.epam.vitebsk.model.entity.Message;

public class MailService {

    private static final String PROTOCOL = "smtp";
    private static final String HOST_PROPERTY = "mail.smtp.host";
    private static final String PORT_PROPERTY = "mail.smtp.port";
    private static final String AUTH_PROPERTY = "mail.smtp.auth";

    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String UNABLE_SEND_MESSAGE = "Can't send message";

    private final String username;
    private final String password;

    private String host;
    private String port;

    public MailService(String host, String port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public MailService(Map<String, String> map) {
        this.host = map.get(HOST);
        this.port = map.get(PORT);
        this.username = map.get(USERNAME);
        this.password = map.get(PASSWORD);
    }

    public void send(Message message) {

        try {
            javax.mail.Message msg = compose(message);

            Transport transport = getSession(PROTOCOL).getTransport(PROTOCOL);

            transport.connect(username, password);

            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());

            transport.close();

        } catch (MessagingException e) {
            throw new SendMessageException(UNABLE_SEND_MESSAGE, e);
        }

    }

    public javax.mail.Message compose(Message message) throws MessagingException {

        String to = message.getRecipient().getUsername();
        String from = message.getSender().getUsername();

        javax.mail.Message msg = new MimeMessage(getSession(PROTOCOL));

        msg.setFrom(new InternetAddress(username));
        InternetAddress[] address = { new InternetAddress(to) };
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(from + " send message to you: " + message.getSubject());
        msg.setSentDate(new Date());
        msg.setText(message.getMessage());

        return msg;
    }

    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put(HOST_PROPERTY, host);
        properties.put(PORT_PROPERTY, port);
        properties.put(AUTH_PROPERTY, true);
        return properties;
    }

    public Session getSession(String protocol) {
        return Session.getDefaultInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
