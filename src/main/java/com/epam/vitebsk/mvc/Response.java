package com.epam.vitebsk.mvc;

public class Response {
	
	private boolean redirect;
	private String url;

	public Response(boolean redirect, String url) {
		this.url = url;
		this.redirect = redirect;
	}
	
	public Response(String url) {
	    this(true, url);
	}	

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
