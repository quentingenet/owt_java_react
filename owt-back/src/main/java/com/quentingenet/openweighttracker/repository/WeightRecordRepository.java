package com.quentingenet.openweighttracker.repository;

import com.quentingenet.openweighttracker.entity.BodyMassIndexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentingenet.openweighttracker.entity.WeightRecordEntity;

@Repository
public interface WeightRecordRepository extends JpaRepository<WeightRecordEntity, Long> {

 WeightRecordEntity findByBmi(BodyMassIndexEntity bmiToFind);
}
