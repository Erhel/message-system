package com.epam.vitebsk.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.epam.vitebsk.entity.Message;

public class MailService {
    
    private boolean auth;
    private String host;
    private String port;
    final private String username;
    final private String password;
    
    public MailService(boolean auth, String host, String port, String username, String password) {
        this.auth = auth;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    
    public MailService(String host, String port) {
        this(false, host, port, null, null);
    }

    public void send(Message message) { 
        try {
            javax.mail.Message msg = compose(message);
            
            Transport transport = getSession("smtp").getTransport("smtp");
            
            if (auth) {
                transport.connect(username, password);
            } else {
                transport.connect();
            }
            
            msg.saveChanges();
            transport.sendMessage(msg, msg.getAllRecipients());
            
            transport.close();
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    public javax.mail.Message compose(Message message) throws MessagingException {
        
        String to = message.getRecipient().getUsername();
        String from = message.getSender().getUsername();
       
        javax.mail.Message msg = new MimeMessage(getSession("smtp"));
        
        msg.setFrom(new InternetAddress(from));
        InternetAddress [] address = {new InternetAddress(to)};
        msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
        msg.setSubject(message.getSubject());
        msg.setSentDate(new Date());
        msg.setText(message.getMessage());
        
        return msg;
    }
    
    public Properties getProperties(String protocol) {
        Properties properties = new Properties();
        properties.put("mail." + protocol + ".host", host);
        properties.put("mail." + protocol + ".port", port);
        properties.put("mail." + protocol + ".auth", auth);
        properties.put("mail." + protocol + ".ssl.enable", "true");
        return properties;
    }
    
    public Session getSession(String protocol) {
        if (auth) {
            return Session.getDefaultInstance(getProperties(protocol), new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        }
        return Session.getDefaultInstance(getProperties(protocol));
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
