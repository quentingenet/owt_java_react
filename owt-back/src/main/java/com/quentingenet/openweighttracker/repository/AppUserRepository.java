package com.quentingenet.openweighttracker.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quentingenet.openweighttracker.entity.AppUserEntity;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

	AppUserEntity findByAppUsername(String appUsername);

	AppUserEntity findByEmailUser(String emailUser);

	Optional<AppUserEntity> findById(Long idUser);

}
