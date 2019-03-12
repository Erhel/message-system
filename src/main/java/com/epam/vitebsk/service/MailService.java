package com.epam.vitebsk.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.epam.vitebsk.entity.Message;

public class MailService {
    
    private boolean auth;
    private String host;
    private String port;
    private String username;
    private String password;
    
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
    
    public Message receive() {
        try {
            Store store = getSession("pop3").getStore("pop3");
            
            if (auth) {
                store.connect(username, password);
            } else {
                store.connect();
            }
            
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            javax.mail.Message[] messages = folder.getMessages();
            
            
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return null;
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
        return properties;
    }
    
    public Session getSession(String protocol) {
        return Session.getDefaultInstance(getProperties(protocol));
    }
}
