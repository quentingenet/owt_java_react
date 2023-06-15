package com.quentingenet.openweighttracker.service;

import com.quentingenet.openweighttracker.entity.AppUserEntity;
import org.springframework.mail.SimpleMailMessage;

public interface PasswordResetTokenService {

  void createPasswordResetTokenForUser(AppUserEntity appUser, String token);

  void changePassword(String token, String newPassword);

  SimpleMailMessage constructEmail(String subject, String body, AppUserEntity appUser);
  SimpleMailMessage constructResetTokenEmail(String contextPath, String token, AppUserEntity appUser);
}
