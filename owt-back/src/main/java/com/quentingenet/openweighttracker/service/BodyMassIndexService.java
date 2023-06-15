package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.entity.BodyMassIndexEntity;

public interface BodyMassIndexService {
  BodyMassIndexEntity bmiCalculator(Double userLastWeight , Double bodySize, Boolean isEuropeanUnitMeasure, Long personId);
  BodyMassIndexEntity getCurrentBim(Long personId);
  void saveBim(BodyMassIndexEntity bimToSave);
  BodyMassIndexEntity updateBim(Long bimId, Long personId);

  void deleteBim(Long bimId);
}
