package com.quentingenet.openweighttracker.dto.appUser;

public class NewPersonToSaveDto {
	// INITIAL DATA
	private boolean isEuropeanUnitMeasure;

	private boolean isAcceptedTerms;

	private Double goalWeight;

	private Double bodySize;

	private boolean isMale;

	private Integer yearBirth;

	// APPUSER
	private String appUsername;

	private String password;

	private String emailUser;

	/* Getters and setters */

	public boolean isEuropeanUnitMeasure() {
		return isEuropeanUnitMeasure;
	}

	public void setEuropeanUnitMeasure(boolean europeanUnitMeasure) {
		isEuropeanUnitMeasure = europeanUnitMeasure;
	}

	public boolean isAcceptedTerms() {
		return isAcceptedTerms;
	}

	public void setAcceptedTerms(boolean acceptedTerms) {
		isAcceptedTerms = acceptedTerms;
	}

	public Double getGoalWeight() {
		return goalWeight;
	}

	public void setGoalWeight(Double goalWeight) {
		this.goalWeight = goalWeight;
	}

	public Double getBodySize() {
		return bodySize;
	}

	public void setBodySize(Double bodySize) {
		this.bodySize = bodySize;
	}

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean male) {
		isMale = male;
	}

	public Integer getYearBirth() {
		return yearBirth;
	}

	public void setYearBirth(Integer yearBirth) {
		this.yearBirth = yearBirth;
	}

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
