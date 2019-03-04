package com.epam.vitebsk.entity;

public class User extends Entity {

	private String username;
	private String password;
	private String displayName;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public User(String username, String password, String displayName, Long id) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.displayName = displayName;
	}
}
