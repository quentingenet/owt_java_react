package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.entity.AppUserEntity;
import com.quentingenet.openweighttracker.entity.PasswordResetTokenEntity;
import com.quentingenet.openweighttracker.repository.AppUserRepository;
import com.quentingenet.openweighttracker.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

  @Autowired
  PasswordResetTokenRepository passwordTokenRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  AppUserRepository appUserRepository;

  public void createPasswordResetTokenForUser(AppUserEntity appUser, String token) {
    PasswordResetTokenEntity resetPasswordToken = new PasswordResetTokenEntity();
    resetPasswordToken.setAppUser(appUser);
    resetPasswordToken.setToken(token);
    Date dateInitial = new Date();
    Long dateInitialTimeToMilliSecond = dateInitial.getTime();
    resetPasswordToken.setExpiryDate(new Date(dateInitialTimeToMilliSecond + 300000L));//Expiration set time to 5minutes.
    passwordTokenRepository.save(resetPasswordToken);
  }

  public SimpleMailMessage constructResetTokenEmail(
      String contextPath, String token, AppUserEntity appUser) {
    String url = contextPath + "/users/changePassword?token=" + token;
    return constructEmail("Reset password - OpenWeightTracker", "*** OPEN WEIGHT TRACKER ***\nClick on the following link to reset your password.\n5 minutes left to change it before expiration." + " \r\n" + url, appUser);
  }

  public SimpleMailMessage constructEmail(String subject, String body,
                                          AppUserEntity appUser) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setSubject(subject);
    email.setText(body);
    email.setTo(appUser.getEmailUser());
    email.setFrom("noreply@openweighttracker.com");
    return email;
  }


  @Override
  public void changePassword(String token, String newPassword) {
    PasswordResetTokenEntity receivedToken = passwordTokenRepository.findByToken(token);
    AppUserEntity appUser = receivedToken.getAppUser();
    if (!isExpired(receivedToken)) {
      appUser.setPassword(passwordEncoder.encode(newPassword));
      appUserRepository.save(appUser);
    } else {
      throw new IllegalArgumentException("TOKEN IS EXPIRED.");
    }
  }

  public boolean isExpired(PasswordResetTokenEntity token) {
    Date currentDate = new Date();
    return currentDate.after(token.getExpiryDate());
  }


}
