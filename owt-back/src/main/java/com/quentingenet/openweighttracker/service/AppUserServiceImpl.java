package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.EmailValidator;
import com.quentingenet.openweighttracker.dto.appUser.AppUserListDto;
import com.quentingenet.openweighttracker.dto.appUser.NewPersonToSaveDto;
import com.quentingenet.openweighttracker.dto.appUser.PersonUpdateAppUserDto;
import com.quentingenet.openweighttracker.dto.appUser.PersonUpdateInitialDataDto;
import com.quentingenet.openweighttracker.entity.AppUserEntity;
import com.quentingenet.openweighttracker.entity.InitialDataEntity;
import com.quentingenet.openweighttracker.entity.PersonEntity;
import com.quentingenet.openweighttracker.entity.Role;
import com.quentingenet.openweighttracker.repository.AppUserRepository;
import com.quentingenet.openweighttracker.repository.PasswordResetTokenRepository;
import com.quentingenet.openweighttracker.repository.PersonRepository;
import com.quentingenet.openweighttracker.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	PersonRepository personRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordResetTokenRepository passwordTokenRepository;

	@Autowired
	EmailValidator emailValidator;
	private final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);


	@Override
	@Transactional
	public void saveNewPerson(NewPersonToSaveDto personToSaveDto) {
		PersonEntity personEntityToSave = new PersonEntity();

		Role roleUser = roleRepository.findByRoleName("ROLE_USER");

		AppUserEntity appUserEntity = new AppUserEntity();
		appUserEntity.setRoles(Arrays.asList(roleUser));

		//For appUsername : replaceAll() is used to be sure that there is no whitespace.
		String appUserNameCheckedWithoutWhiteSpace = personToSaveDto.getAppUsername().replaceAll("\\s+","");
		appUserEntity.setAppUsername(appUserNameCheckedWithoutWhiteSpace);

		if (emailValidator.isValidEmail(personToSaveDto.getEmailUser())) {
			appUserEntity.setEmailUser(personToSaveDto.getEmailUser());
		}else {
			appUserEntity.setEmailUser(null);
		}
		//For password :  replaceAll() is used to be sure that there is no whitespace.
		String passwordCheckedWithoutWhiteSpace = personToSaveDto.getPassword().replaceAll("\\s+","");
		appUserEntity.setPassword(new BCryptPasswordEncoder().encode(passwordCheckedWithoutWhiteSpace));

		InitialDataEntity initialDataEntity = new InitialDataEntity();
		initialDataEntity.setBodySize(personToSaveDto.getBodySize());
		initialDataEntity.setRegisterUserDate(LocalDate.now());
		initialDataEntity.setGoalWeight(personToSaveDto.getGoalWeight());
		initialDataEntity.setIsMale(personToSaveDto.getIsMale());
		initialDataEntity.setIsEuropeanUnitMeasure(personToSaveDto.getIsEuropeanUnitMeasure());
		initialDataEntity.setYearBirth(personToSaveDto.getYearBirth());
		initialDataEntity.setAcceptedTerms(personToSaveDto.getAcceptedTerms());

		personEntityToSave.setAppUserPerson(appUserEntity);
		personEntityToSave.setUserInitData(initialDataEntity);

		personRepository.save(personEntityToSave);

	}

	@Override
	public AppUserEntity findUserByAppUsername(String appUsername) {
		return appUserRepository.findByAppUsername(appUsername);

	}

	@Override
	public AppUserEntity findAppUserById(Long idUser) {
		return appUserRepository.findById(idUser).orElseThrow(NoSuchElementException::new);
	}

	@Override
	public PersonUpdateInitialDataDto updateInitialDataPerson(Long appUserId,
																														PersonUpdateInitialDataDto personToUpdateFromUser) {
		PersonEntity personUpdated = personRepository.findById(appUserId).orElseThrow(NoSuchElementException::new);

		if (personToUpdateFromUser != null){
			try{
				if (personToUpdateFromUser.getBodySize() !=  null) {
					personUpdated.getUserInitData().setBodySize(personToUpdateFromUser.getBodySize());
				}
				if (personToUpdateFromUser.getGoalWeight() != null) {
					personUpdated.getUserInitData().setGoalWeight(personToUpdateFromUser.getGoalWeight());
				}
				if (personToUpdateFromUser.getIsMale() != null) {
					personUpdated.getUserInitData().setIsMale(personToUpdateFromUser.getIsMale());
				}
				if (personToUpdateFromUser.getYearBirth() != null){
					personUpdated.getUserInitData().setYearBirth(personToUpdateFromUser.getYearBirth());
				}
				personRepository.save(personUpdated);

			} catch (NullPointerException e) {
				logger.info("Updating initial data for user n°{} is raising Null Pointer Exception.", appUserId);
			}
			return personToUpdateFromUser;
		}else {
			return new PersonUpdateInitialDataDto();
		}
	}

	@Override
	public PersonUpdateAppUserDto updateAppUserPerson(Long appUserId, PersonUpdateAppUserDto personToUpdateFromUser) {
		PersonEntity personUpdated = personRepository.findById(appUserId).orElseThrow(NoSuchElementException::new);
		if (personToUpdateFromUser != null) {
			try {
				if (personToUpdateFromUser.getAppUsername() != null) {
					personUpdated.getAppUserPerson().setAppUsername(personToUpdateFromUser.getAppUsername());
				}
				if (personToUpdateFromUser.getPassword() != null) {
					personUpdated.getAppUserPerson()
							.setPassword(new BCryptPasswordEncoder().encode(personToUpdateFromUser.getPassword()));
				}
				if (emailValidator.isValidEmail(personToUpdateFromUser.getEmailUser())) {
					personUpdated.getAppUserPerson().setEmailUser(personToUpdateFromUser.getEmailUser());
				} else {
					personUpdated.getAppUserPerson().setEmailUser(null);
				}
				personRepository.save(personUpdated);
			} catch (NullPointerException e) {
				logger.info("NULL POINTER EXCEPTION WHEN UPDATE AN APPUSER.");
			}

			return personToUpdateFromUser;
		} else {
			return new PersonUpdateAppUserDto();
		}
	}

	@Override
	public List<AppUserListDto> getAllUsers() {
		List<AppUserEntity> appUserListEntity = appUserRepository.findAll();
		List<AppUserListDto> appUsersToReturn = new ArrayList<AppUserListDto>();

		for (AppUserEntity appUserEntity : appUserListEntity) {
			AppUserListDto appUserListDto = new AppUserListDto();
			appUserListDto.setAppUserName(appUserEntity.getAppUsername());
			appUserListDto.setIdAppUser(appUserEntity.getIdUser());
			appUsersToReturn.add(appUserListDto);
		}
		return appUsersToReturn;
	}

}
