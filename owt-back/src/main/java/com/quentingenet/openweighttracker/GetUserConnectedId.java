package com.quentingenet.openweighttracker;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.quentingenet.openweighttracker.entity.AppUserEntity;
import com.quentingenet.openweighttracker.repository.AppUserRepository;


@Component
public class GetUserConnectedId {

	@Autowired
	AppUserRepository appUserRepository;

	private final Logger logger = LoggerFactory.getLogger(GetUserConnectedId.class);

	public Long getAppUserConnectedId(Principal principal) {
		if (!(principal instanceof UsernamePasswordAuthenticationToken)) {
			throw new RuntimeException(("User not found"));
		}
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
		AppUserEntity appUserFinded = appUserRepository.findByAppUsername(token.getName());
		logger.info("USER : {} IS FINDED IN DATABASE.", appUserFinded.getAppUsername());
		return appUserFinded.getIdUser();
	}
}
