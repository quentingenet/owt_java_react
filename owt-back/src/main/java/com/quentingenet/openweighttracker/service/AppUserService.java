package com.quentingenet.openweighttracker.service;

import java.util.List;

import com.quentingenet.openweighttracker.dto.appUser.AppUserListDto;
import com.quentingenet.openweighttracker.dto.appUser.NewPersonToSaveDto;
import com.quentingenet.openweighttracker.dto.appUser.PersonUpdateAppUserDto;
import com.quentingenet.openweighttracker.dto.appUser.PersonUpdateInitialDataDto;
import com.quentingenet.openweighttracker.entity.AppUserEntity;

public interface AppUserService {

	void saveNewPerson(NewPersonToSaveDto personToSaveDto);

	AppUserEntity findUserByAppUsername (String appUsername);

	PersonUpdateInitialDataDto updateInitialDataPerson(Long appUserId, PersonUpdateInitialDataDto personToUpdateFromUser);

	PersonUpdateAppUserDto updateAppUserPerson(Long appUserId, PersonUpdateAppUserDto personToUpdateFromUser);

	AppUserEntity findAppUserById(Long idUser);

	List<AppUserListDto> getAllUsers();



}
