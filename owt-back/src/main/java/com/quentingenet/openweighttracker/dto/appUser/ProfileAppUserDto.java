package com.quentingenet.openweighttracker.dto.appUser;

import com.quentingenet.openweighttracker.entity.BodyMassIndexEntity;
import com.quentingenet.openweighttracker.entity.WeightRecordEntity;

public class ProfileAppUserDto {

	private String appUserName;

	private Double goalWeight;

	private Double bodySize;

	private Double currentBimValue;

	private WeightRecordEntity lastWeight;

	private Boolean isMale;

	private Boolean isEuropeanUnitMeasure;

	private Integer age;

	/*Getters and setters*/
	public String getAppUserName() {
		return appUserName;
	}

	public void setAppUserName(String appUserName) {
		this.appUserName = appUserName;
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

	public Double getCurrentBimValue() {
		return currentBimValue;
	}

	public void setCurrentBimValue(Double currentBimValue) {
		this.currentBimValue = currentBimValue;
	}

	public Boolean getMale() {
		return isMale;
	}

	public void setMale(Boolean male) {
		isMale = male;
	}

	public Boolean getEuropeanUnitMeasure() {
		return isEuropeanUnitMeasure;
	}

	public void setEuropeanUnitMeasure(Boolean europeanUnitMeasure) {
		isEuropeanUnitMeasure = europeanUnitMeasure;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public WeightRecordEntity getLastWeight() {
		return lastWeight;
	}

	public void setLastWeight(WeightRecordEntity lastWeight) {
		this.lastWeight = lastWeight;
	}
}
