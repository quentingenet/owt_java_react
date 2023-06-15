package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.dto.appUser.ProfileAppUserDto;

public interface HomeUserService {

	ProfileAppUserDto getDataForUserProfile(Long appUserId);

}
