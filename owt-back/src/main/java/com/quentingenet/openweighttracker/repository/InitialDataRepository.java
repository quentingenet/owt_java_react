package com.quentingenet.openweighttracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentingenet.openweighttracker.entity.InitialDataEntity;

@Repository
public interface InitialDataRepository extends JpaRepository<InitialDataEntity, Long> {
	
	
}
