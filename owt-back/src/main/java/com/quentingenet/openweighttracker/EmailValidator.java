package com.quentingenet.openweighttracker;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {

  //TODO revoir email validation car la regex ne match pâs le .com ( le point)
  //^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$
  //^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$  --> regex intial ne matchant pas le point
  public boolean isValidEmail(String emailAddress) {
    if (emailAddress != null) {
      return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]{2,40}+@(?:[a-zA-Z0-9-]{2,40}+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
          .matcher(emailAddress).matches();
    }else {
      return false;
    }
  }
}
