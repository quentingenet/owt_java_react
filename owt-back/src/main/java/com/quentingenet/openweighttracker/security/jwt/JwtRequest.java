package com.quentingenet.openweighttracker.security.jwt;

public class JwtRequest {

	private String appUsername;
	private String password;

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

}
