package com.quentingenet.openweighttracker.service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailForNewUserServiceImpl implements MailForNewUserService {

    @Override
    public SimpleMailMessage constructEmail(String subject, String body) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo("genetquentin@gmail.com");
        email.setFrom("noreply@openweighttracker.com");
        return email;
    }
    @Override
    public SimpleMailMessage constructNewUserEmail(String appUsername) {
        return constructEmail("NEW USER : ".concat(appUsername).concat(" - OpenWeightTracker") , "*** OPEN WEIGHT TRACKER ***\nNEW USER : ".concat(appUsername));
    }


}
