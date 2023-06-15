package com.quentingenet.openweighttracker.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class Privilege implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_privilege")
  private Long idPrivileges;
  private String name;
  @ManyToMany(mappedBy = "privileges")
  private Collection<Role> roles;

  /*Getters and setters*/
  public Long getIdPrivileges() {
    return idPrivileges;
  }

  public void setIdPrivileges(Long idPrivileges) {
    this.idPrivileges = idPrivileges;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Collection<Role> getRoles() {
    return roles;
  }
  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

}
