package com.quentingenet.openweighttracker.dto.appUser;

public class PersonUpdateInitialDataDto {

	private Double goalWeight;

	private Double bodySize;

	private boolean isMale;

	private boolean isEuropeanUnitMeasure;

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

	public boolean isMale() {
		return isMale;
	}

	public void setMale(boolean male) {
		isMale = male;
	}

	public boolean isEuropeanUnitMeasure() {
		return isEuropeanUnitMeasure;
	}

	public void setEuropeanUnitMeasure(boolean europeanUnitMeasure) {
		isEuropeanUnitMeasure = europeanUnitMeasure;
	}

	public Integer getYearBirth() {
		return yearBirth;
	}

	public void setYearBirth(Integer yearBirth) {
		this.yearBirth = yearBirth;
	}
}
