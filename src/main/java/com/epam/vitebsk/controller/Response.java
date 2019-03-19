package com.epam.vitebsk.controller;

public class Response {

    private String url;
    private boolean redirect;

    public Response(boolean redirect, String url) {
        this.url = url;
        this.redirect = redirect;
    }

    public Response(String url) {
        this(true, url);
    }

    public String getUrl() {
        return url;
    }

    public boolean isRedirect() {
        return redirect;
    }
}
