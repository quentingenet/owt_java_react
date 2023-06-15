package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.entity.BodyMassIndexEntity;
import com.quentingenet.openweighttracker.entity.PersonEntity;
import com.quentingenet.openweighttracker.repository.BodyMassIndexRepository;
import com.quentingenet.openweighttracker.repository.PersonRepository;
import com.quentingenet.openweighttracker.repository.WeightRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BodyMassIndexServiceImpl implements BodyMassIndexService{

  @Autowired
  PersonRepository personRepository;

  @Autowired
  BodyMassIndexRepository bodyMassIndexRepository;

  @Autowired
  WeightRecordRepository weightRecordRepository;

  private final Logger logger = LoggerFactory.getLogger(BodyMassIndexServiceImpl.class);

  //TODO  trouver ou sauvegarder le BIM et quand
  @Override
  public BodyMassIndexEntity bmiCalculator(Double userLastWeight, Double bodySize, Boolean isEuropeanUnitMeasure, Long personId) {
    //MUST TO CHECK WHICH IS VALUE OF THE BOOLEAN IN THE FRONT END TO SAVE GOOD FORMAT DATA : US OR EUROPEAN
		/*
		 BMI = weight ÷ height²
    In metric units: BMI = weight (kg) ÷ height² (meters)
    In US units: BMI = weight (lb) ÷ height2 (FEET and inches) * 703
    POUR MESURE US : nécessité de feet and inch... Donc faire deux inputform : 1/ feet 2/ inch, et convertir coté front
		  */
    BodyMassIndexEntity userBmi = new BodyMassIndexEntity();
    if (isEuropeanUnitMeasure) {
      //convert cm to meter
      bodySize = bodySize/100;
    }
    userBmi.setBodyMassIndex(userLastWeight / (bodySize * bodySize));
    return userBmi;
  }

  @Override
  public BodyMassIndexEntity getCurrentBim(Long personId) {
//FIND ALL BY PERSON ID
    PersonEntity personFound = personRepository.findById(personId).orElseThrow(NoSuchElementException::new);
    try{
      BodyMassIndexEntity currentBmi = personFound.getWeightsList().get(personFound.getWeightsList().size()-1).getBmi();
      return currentBmi;
    } catch (IndexOutOfBoundsException e){
      logger.info("INDEX OUT OF BOUND EXCEPTION BECAUSE NO BIM IN ARRAYLIST");
    }
    return null;
  }

  @Override
  public void saveBim(BodyMassIndexEntity bimToSave) {
    bodyMassIndexRepository.save(bimToSave);
  }

  @Override
  public BodyMassIndexEntity updateBim(Long bimId, Long personId) {
    return null;
  }

  @Override
  public void deleteBim(Long bimId) {
    try {
      bodyMassIndexRepository.deleteById(bimId);
      logger.info("BIM IS SUPRESSED WITH ID :{}", bimId);

    } catch (IndexOutOfBoundsException e){
      logger.info("INDEX OUT OF BOUND EXCEPTION, CAN'T DELETE BIM");
    }
  }
}
