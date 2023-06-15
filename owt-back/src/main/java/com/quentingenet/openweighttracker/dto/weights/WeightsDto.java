package com.quentingenet.openweighttracker.dto.weights;

import java.util.ArrayList;
import java.util.List;

import com.quentingenet.openweighttracker.entity.WeightRecordEntity;

public class WeightsDto {

	private List<WeightRecordEntity> weightsList = new ArrayList<WeightRecordEntity>();

	/* Getters and setters */
	public List<WeightRecordEntity> getWeightsList() {
		return weightsList;
	}

	public void setWeightsList(List<WeightRecordEntity> weightsList) {
		this.weightsList = weightsList;
	}

}
