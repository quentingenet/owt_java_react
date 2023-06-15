package com.quentingenet.openweighttracker.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PasswordResetTokenEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idPasswordResetToken;

  private String token;

  @OneToOne(targetEntity = AppUserEntity.class, fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "id_user")
  private AppUserEntity appUser;

  private Date expiryDate;

  /*Getters and setters*/
  public Long getIdPasswordResetToken() {
    return idPasswordResetToken;
  }

  public void setIdPasswordResetToken(Long idPasswordResetToken) {
    this.idPasswordResetToken = idPasswordResetToken;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public AppUserEntity getAppUser() {
    return appUser;
  }

  public void setAppUser(AppUserEntity appUser) {
    this.appUser = appUser;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }
  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }
}
