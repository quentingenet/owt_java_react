package com.quentingenet.openweighttracker.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class PersonEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_person")
	private Long idPerson;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_initial_data")
	private InitialDataEntity userInitData;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private AppUserEntity appUserPerson;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "person")
	private List<WeightRecordEntity> weightsList = new ArrayList<WeightRecordEntity>();

	/* Getters and setters */
	public Long getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}

	public InitialDataEntity getUserInitData() {
		return userInitData;
	}

	public void setUserInitData(InitialDataEntity userInitData) {
		this.userInitData = userInitData;
	}

	public AppUserEntity getAppUserPerson() {
		return appUserPerson;
	}

	public void setAppUserPerson(AppUserEntity appUserPerson) {
		this.appUserPerson = appUserPerson;
	}

	public List<WeightRecordEntity> getWeightsList() {
		return weightsList;
	}

	public void setWeightsList(List<WeightRecordEntity> weightsList) {
		this.weightsList = weightsList;
	}
/*
	public List<BodyMassIndexEntity> getBodyMassIndexList() {
		return bodyMassIndexEntityList;
	}

	public void setBodyMassIndexEntityList(List<BodyMassIndexEntity> bodyMassIndexEntityList) {
		this.bodyMassIndexEntityList = bodyMassIndexEntityList;
	}
 */
}
