package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.dto.appUser.ProfileAppUserDto;
import com.quentingenet.openweighttracker.entity.PersonEntity;
import com.quentingenet.openweighttracker.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class HomeUserServiceImpl implements HomeUserService {
	//TODO CALCULATE AGE FOR USER WITH HIS YEARBIRTH WITH MORE PRECISIONS...
	@Autowired
	PersonRepository personRepository;

	@Autowired
	BodyMassIndexServiceImpl bodyMassIndexService;

	private final Logger logger = LoggerFactory.getLogger(HomeUserServiceImpl.class);
	@Override
	public ProfileAppUserDto getDataForUserProfile(Long appUserId) {

		PersonEntity personEntityConnected = personRepository.findById(appUserId).orElseThrow(NoSuchElementException::new);

		ProfileAppUserDto profileDataUser = new ProfileAppUserDto();

		profileDataUser.setAppUserName(personEntityConnected.getAppUserPerson().getAppUsername());
		profileDataUser.setBodySize(personEntityConnected.getUserInitData().getBodySize());
		profileDataUser.setGoalWeight(personEntityConnected.getUserInitData().getGoalWeight());
		profileDataUser.setMale(personEntityConnected.getUserInitData().getIsMale());
		profileDataUser.setEuropeanUnitMeasure(personEntityConnected.getUserInitData().getIsEuropeanUnitMeasure());
		profileDataUser.setAge(LocalDate.now().getYear() - personEntityConnected.getUserInitData().getYearBirth());
		try{
			profileDataUser.setLastWeight(personEntityConnected.getWeightsList().get(personEntityConnected.getWeightsList().size()-1));
		}catch (IndexOutOfBoundsException e){
			logger.info("INDEX OUT OF BOUNDS EXCEPTION BECAUSE THERE IS NO WEIGHTS IN USER LIST YET.");
		}
		if (personEntityConnected.getWeightsList().size() > 1) {
			profileDataUser.setCurrentBimValue(bodyMassIndexService.getCurrentBim(personEntityConnected.getIdPerson()).getBodyMassIndex());
		}else {
			profileDataUser.setCurrentBimValue(null);
		}
		return profileDataUser;
	}
}
