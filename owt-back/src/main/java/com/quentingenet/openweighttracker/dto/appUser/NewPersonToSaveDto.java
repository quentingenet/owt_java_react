package com.quentingenet.openweighttracker.dto.appUser;

public class NewPersonToSaveDto {
	// INITIAL DATA
	private Boolean isEuropeanUnitMeasure;

	private Double goalWeight;

	private Double bodySize;

	private Boolean isMale;

	private Integer yearBirth;

	// APPUSER
	private String appUsername;

	private String password;

	private String emailUser;

	/* Getters and setters */
	public Boolean getIsEuropeanUnitMeasure() {
		return isEuropeanUnitMeasure;
	}

	public void setIsEuropeanUnitMeasure(Boolean isEuropeanUnitMeasure) {
		this.isEuropeanUnitMeasure = isEuropeanUnitMeasure;
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

	public Boolean getIsMale() {
		return isMale;
	}

	public void setIsMale(Boolean isMale) {
		this.isMale = isMale;
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
