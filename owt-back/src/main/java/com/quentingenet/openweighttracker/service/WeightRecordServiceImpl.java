package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.dto.weights.WeightRecordDto;
import com.quentingenet.openweighttracker.dto.weights.WeightsDto;
import com.quentingenet.openweighttracker.entity.BodyMassIndexEntity;
import com.quentingenet.openweighttracker.entity.PersonEntity;
import com.quentingenet.openweighttracker.entity.WeightRecordEntity;
import com.quentingenet.openweighttracker.repository.PersonRepository;
import com.quentingenet.openweighttracker.repository.WeightRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WeightRecordServiceImpl implements WeightRecordService {

	@Autowired
	WeightRecordRepository weightRecordRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	BodyMassIndexServiceImpl bodyMassIndexService;

	private final Logger logger = LoggerFactory.getLogger(WeightRecordServiceImpl.class);

	public WeightRecordDto deleteWeightById(Long idUserConnected, Long weightRecordToDeleteId) {
		WeightRecordEntity weightFound = weightRecordRepository.findById(weightRecordToDeleteId)
				.orElseThrow(NoSuchElementException::new);
		WeightRecordDto weightRecordToDelete = new WeightRecordDto();
		weightRecordToDelete.setWeightValue(weightFound.getWeightValue());
		weightRecordToDelete.setWeightRecordDate(weightFound.getWeightRecordDate());
		weightRecordToDelete.setIdWeightRecord(weightFound.getIdWeightRecord());

		logger.info("THIS IS THE WEIGHT ID TO DELETE : {}", weightRecordToDeleteId);
		weightRecordRepository.deleteById(weightRecordToDeleteId);
		logger.info("Weight with id n°{} is deleted now", weightRecordToDeleteId);
		return weightRecordToDelete;
	}

	@Override
	public WeightsDto getAllWeights(Long idUserConnected) {
		logger.info("Seeking for all weights");
		WeightsDto weightsToShow = new WeightsDto();
		PersonEntity personToShow = personRepository.findById(idUserConnected).orElseThrow(NoSuchElementException::new);
		weightsToShow.setWeightsList(personToShow.getWeightsList());
		return weightsToShow;
	}

	@Override
	public WeightRecordDto getWeight(Long id) {
		logger.info("Seeking for weight with id n°{}", id);
		WeightRecordEntity weightFound = weightRecordRepository.findById(id).orElseThrow(NoSuchElementException::new);
		WeightRecordDto weightToShow = new WeightRecordDto();
		weightToShow.setIdWeightRecord(weightFound.getIdWeightRecord());
		weightToShow.setWeightValue(weightFound.getWeightValue());
		weightToShow.setWeightRecordDate(weightFound.getWeightRecordDate());
		weightToShow.setPercentBodyWater(weightFound.getPercentBodyWater());
		weightToShow.setPercentBoneMass(weightFound.getPercentBoneMass());
		weightToShow.setPercentFatMass(weightFound.getPercentFatMass());
		weightToShow.setPercentMuscularMass(weightFound.getPercentMuscularMass());
		return weightToShow;
	}

	@Override
	public WeightRecordDto saveWeightRecordByIdUser(Long idUser, WeightRecordDto weightRecordFromUser) {

		PersonEntity personToSave = personRepository.findById(idUser).orElseThrow(NoSuchElementException::new);
		WeightRecordEntity newSavedWeightRecord = new WeightRecordEntity();

		newSavedWeightRecord.setPerson(personToSave);
		newSavedWeightRecord.setWeightValue(weightRecordFromUser.getWeightValue());
		newSavedWeightRecord.setPercentBodyWater(weightRecordFromUser.getPercentBodyWater());
		newSavedWeightRecord.setPercentBoneMass(weightRecordFromUser.getPercentBoneMass());
		newSavedWeightRecord.setPercentFatMass(weightRecordFromUser.getPercentFatMass());
		newSavedWeightRecord.setPercentMuscularMass(weightRecordFromUser.getPercentMuscularMass());
		newSavedWeightRecord.setWeightRecordDate(LocalDate.now());

		BodyMassIndexEntity bimToSave = bodyMassIndexService.bmiCalculator(weightRecordFromUser.getWeightValue(),
				personToSave.getUserInitData().getBodySize(),
				personToSave.getUserInitData().getIsEuropeanUnitMeasure(),
				idUser);

		newSavedWeightRecord.setBmi(bimToSave);

		weightRecordRepository.save(newSavedWeightRecord);
		//bimToSave.setWeightRecord(newSavedWeightRecord);
		bodyMassIndexService.saveBim(bimToSave);

		List<WeightRecordEntity> weightsListToUpdateList = personToSave.getWeightsList();
		weightsListToUpdateList.add(newSavedWeightRecord);
		personRepository.save(personToSave);
		logger.info("New weight :{} kg is saved in database now.", newSavedWeightRecord.getWeightValue());
		logger.info("New basic mass Index is calculated and saved : {}", bimToSave.getBodyMassIndex());
		return weightRecordFromUser;
	}

	@Override
	public WeightRecordDto updateWeight(Long idWeight, WeightRecordDto weightRecordFromUser, Long idUser) {
		WeightRecordEntity weightRecordToUpdate = weightRecordRepository.findById(idWeight)
				.orElseThrow(NoSuchElementException::new);
		PersonEntity personFound = personRepository.findById(idUser).orElseThrow(NoSuchElementException::new);
		if (weightRecordFromUser != null) {
			try{
				weightRecordToUpdate.setWeightRecordDate(weightRecordToUpdate.getWeightRecordDate());
				if (weightRecordFromUser.getWeightValue() != null){
					weightRecordToUpdate.setWeightValue(weightRecordFromUser.getWeightValue());
				}
				weightRecordToUpdate.setPercentBodyWater(weightRecordFromUser.getPercentBodyWater());
				weightRecordToUpdate.setPercentBoneMass(weightRecordFromUser.getPercentBoneMass());
				weightRecordToUpdate.setPercentFatMass(weightRecordFromUser.getPercentFatMass());
				weightRecordToUpdate.setPercentMuscularMass(weightRecordFromUser.getPercentMuscularMass());

				BodyMassIndexEntity bimToSave = bodyMassIndexService.bmiCalculator(weightRecordFromUser.getWeightValue(),
						personFound.getUserInitData().getBodySize(),
						personFound.getUserInitData().getIsEuropeanUnitMeasure(),
						idUser);

				weightRecordToUpdate.setBmi(bimToSave);

				weightRecordRepository.save(weightRecordToUpdate);
			}catch (NullPointerException e){
				logger.info("Null pointer exception raised because weight to update is null");
			}
			return weightRecordFromUser;
		}
		return  new WeightRecordDto();
	}

}
