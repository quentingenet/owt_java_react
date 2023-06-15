package com.quentingenet.openweighttracker.security.jwt;

import org.springframework.http.HttpHeaders;

public class JwtResponse {

	private String userName;

	public JwtResponse(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
