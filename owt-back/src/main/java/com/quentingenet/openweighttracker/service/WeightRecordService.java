package com.quentingenet.openweighttracker.service;


import com.quentingenet.openweighttracker.dto.weights.WeightRecordDto;
import com.quentingenet.openweighttracker.dto.weights.WeightsDto;

public interface WeightRecordService {

	WeightsDto getAllWeights(Long idUserConnected);

	WeightRecordDto getWeight(Long id);

	WeightRecordDto saveWeightRecordByIdUser(Long idUser, WeightRecordDto weightRecordFromUserUser);

	WeightRecordDto deleteWeightById(Long idUserConnected, Long weightRecordToDeleteId);

	WeightRecordDto updateWeight(Long id, WeightRecordDto weightRecordFromUserUser, Long idUser);

}
