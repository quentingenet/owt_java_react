package com.quentingenet.openweighttracker.controller;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quentingenet.openweighttracker.GetUserConnectedId;
import com.quentingenet.openweighttracker.dto.appUser.ProfileAppUserDto;
import com.quentingenet.openweighttracker.entity.AppUserEntity;
import com.quentingenet.openweighttracker.repository.AppUserRepository;
import com.quentingenet.openweighttracker.service.HomeUserServiceImpl;

@RestController
@RequestMapping("/home")
public class HomeUserController {

	private final Logger logger = LoggerFactory.getLogger(HomeUserController.class);

	@Autowired
	GetUserConnectedId getUserConnectedId;

	@Autowired
	HomeUserServiceImpl homeUserServiceImpl;

	@Autowired
	AppUserRepository appUserRepository;

	@GetMapping
	public ResponseEntity<ProfileAppUserDto> getProfileData(Principal principal) {
		logger.info("GET /home");
		Long appUserConnectedId = getUserConnectedId.getAppUserConnectedId(principal);
		AppUserEntity appUserFound = appUserRepository.findById(appUserConnectedId).orElseThrow(NoSuchElementException::new);
		if (appUserConnectedId.equals(appUserFound.getIdUser())) {
			return new ResponseEntity<>(homeUserServiceImpl.getDataForUserProfile(appUserConnectedId),
					HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
}
