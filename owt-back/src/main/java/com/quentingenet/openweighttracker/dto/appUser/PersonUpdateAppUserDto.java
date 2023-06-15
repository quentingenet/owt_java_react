package com.quentingenet.openweighttracker.dto.appUser;

public class PersonUpdateAppUserDto {

	private String appUsername;

	private String password;

	private String emailUser;

	/* Getters and setters */
	public String getAppUsername() {
		return appUsername;
	}

	public void setAppUsername(String appUsername) {
		this.appUsername = appUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

}
