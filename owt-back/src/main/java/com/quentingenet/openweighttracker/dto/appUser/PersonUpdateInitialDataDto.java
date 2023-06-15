package com.quentingenet.openweighttracker.dto.appUser;

public class PersonUpdateInitialDataDto {

	private Double goalWeight;

	private Double bodySize;

	private Boolean isMale;

	private Boolean isEuropeanUnitMeasure;

	private Integer yearBirth;

	/* Getters and setters */
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

	public Integer getYearBirth() {
		return yearBirth;
	}

	public void setYearBirth(Integer yearBirth) {
		this.yearBirth = yearBirth;
	}

	public Boolean getIsMale() {
		return isMale;
	}

	public void setIsMale(Boolean isMale) {
		this.isMale = isMale;
	}

	public Boolean getIsEuropeanUnitMeasure() {
		return isEuropeanUnitMeasure;
	}

	public void setIsEuropeanUnitMeasure(Boolean isEuropeanUnitMeasure) {
		this.isEuropeanUnitMeasure = isEuropeanUnitMeasure;
	}



}
