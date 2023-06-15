package com.quentingenet.openweighttracker.repository;

import com.quentingenet.openweighttracker.entity.BodyMassIndexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BodyMassIndexRepository extends JpaRepository<BodyMassIndexEntity, Long> {
}
