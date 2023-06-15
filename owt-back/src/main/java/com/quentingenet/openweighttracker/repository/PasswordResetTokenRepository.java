package com.quentingenet.openweighttracker.repository;

import com.quentingenet.openweighttracker.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository <PasswordResetTokenEntity, Long>{

  PasswordResetTokenEntity findByToken(String token);
}
