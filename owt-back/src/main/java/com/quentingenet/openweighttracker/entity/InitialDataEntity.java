
package com.quentingenet.openweighttracker.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "initial_data")
@Entity
public class InitialDataEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_initial_data")
	private Long idInitialData;

	@Column(name = "register_user_date", nullable = false)
	private LocalDate registerUserDate;

	@Min(2)
	@Max(1500)
	@Column(name = "goal_weight", nullable = false, precision = 1)
	private Double goalWeight;

	@Min(25)
	@Max(300)
	@Column(name = "body_size", nullable = false, precision = 1)
	private Double bodySize;

	@Column(name = "is_male", nullable = false)
	private boolean isMale;

	@Column(name = "is_european_unit_measure", nullable = false)
	private boolean isEuropeanUnitMeasure;
	// kg = lbs(pounds) cm = inch metre = feet
	@Column(name = "is_accepted_terms", nullable = false)
	private boolean isAcceptedTerms;

	@Min(1900)
	@Max(2023)
	@Column(name = "year_birth", nullable = false, length = 5)
	private Integer yearBirth;



	/* Getters and setters */

	public Long getIdInitialData() {
		return idInitialData;
	}

	public void setIdInitialData(Long idInitialData) {
		this.idInitialData = idInitialData;
	}

	public LocalDate getRegisterUserDate() {
		return registerUserDate;
	}

	public void setRegisterUserDate(LocalDate registerUserDate) {
		this.registerUserDate = registerUserDate;
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

	public Integer getYearBirth() {
		return yearBirth;
	}

	public void setYearBirth(Integer yearBirth) {
		this.yearBirth = yearBirth;
	}
}
