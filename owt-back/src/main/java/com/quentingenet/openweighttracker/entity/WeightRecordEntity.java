package com.quentingenet.openweighttracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "weight_record")
public class WeightRecordEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_weight_record", nullable = false)
	private Long idWeightRecord;


	@JsonIgnore // JsonIgnore annotation because circular problem with Jackson and entity.
	@ManyToOne(fetch = FetchType.LAZY)
	private PersonEntity person;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_bmi")
	private BodyMassIndexEntity bmi;

	@Column(name = "weight_record_date", nullable = false)
	private LocalDate weightRecordDate;

	@Min(2)
	@Max(1500)
	@Column(name = "weight_value", nullable = false, precision = 1)
	private Double weightValue;

	@Min(2)
	@Max(99)
	@Column(name = "percent_fat_mass", nullable = true, precision = 1)
	private Double percentFatMass;

	@Min(2)
	@Max(99)
	@Column(name = "percent_muscular_mass", nullable = true, precision = 1)
	private Double percentMuscularMass;

	@Min(2)
	@Max(99)
	@Column(name = "percent_body_water", nullable = true, precision = 1)
	private Double percentBodyWater;

	@Min(2)
	@Max(99)
	@Column(name = "percent_bone_mass", nullable = true, precision = 1)
	private Double percentBoneMass;

	/* Getters and setters */
	public Long getIdWeightRecord() {
		return idWeightRecord;
	}

	public void setIdWeightRecord(Long idWeightRecord) {
		this.idWeightRecord = idWeightRecord;
	}

	public PersonEntity getPerson() {
		return person;
	}

	public void setPerson(PersonEntity person) {
		this.person = person;
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

	public BodyMassIndexEntity getBmi() {
		return bmi;
	}

	public void setBmi(BodyMassIndexEntity bmi) {
		this.bmi = bmi;
	}
}
