package com.epam.vitebsk.entity;

public class Message extends Entity {

    private String subject;
    private String message;
    private User sender;
    private User recipient;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Message(String subject, String message, User sender, User recipient) {
        this.subject = subject;
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

}
