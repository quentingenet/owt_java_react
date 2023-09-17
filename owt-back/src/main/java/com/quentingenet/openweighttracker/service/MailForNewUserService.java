package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.dto.appUser.NewPersonToSaveDto;
import com.quentingenet.openweighttracker.entity.AppUserEntity;
import org.springframework.mail.SimpleMailMessage;

public interface MailForNewUserService {

    SimpleMailMessage constructNewUserEmail(String appUsername);
    SimpleMailMessage constructEmail(String subject, String body);
}
