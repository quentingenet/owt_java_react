package com.quentingenet.openweighttracker.dto.weights;

import java.time.LocalDate;

public class WeightRecordDto {

	private Long idWeightRecord;

	private LocalDate weightRecordDate;

	private Double weightValue;

	private Double percentFatMass;

	private Double percentMuscularMass;

	private Double percentBodyWater;

	private Double percentBoneMass;

	/* Getters and setters */
	public Long getIdWeightRecord() {
		return idWeightRecord;
	}

	public void setIdWeightRecord(Long idWeightRecord) {
		this.idWeightRecord = idWeightRecord;
	}

	public LocalDate getWeightRecordDate() {
		return weightRecordDate;
	}

	public void setWeightRecordDate(LocalDate weightRecordDate) {
		this.weightRecordDate = weightRecordDate;
	}

	public Double getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(Double weightValue) {
		this.weightValue = weightValue;
	}

	public Double getPercentFatMass() {
		return percentFatMass;
	}

	public void setPercentFatMass(Double percentFatMass) {
		this.percentFatMass = percentFatMass;
	}

	public Double getPercentMuscularMass() {
		return percentMuscularMass;
	}

	public void setPercentMuscularMass(Double percentMuscularMass) {
		this.percentMuscularMass = percentMuscularMass;
	}

	public Double getPercentBodyWater() {
		return percentBodyWater;
	}

	public void setPercentBodyWater(Double percentBodyWater) {
		this.percentBodyWater = percentBodyWater;
	}

	public Double getPercentBoneMass() {
		return percentBoneMass;
	}

	public void setPercentBoneMass(Double percentBoneMass) {
		this.percentBoneMass = percentBoneMass;
	}

}
