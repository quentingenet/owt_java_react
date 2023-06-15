package com.quentingenet.openweighttracker.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Table(name = "role")
@Entity
public class Role implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_role")
  private Long idRole;
  @Column(name = "role_name")
  private String roleName;
  @ManyToMany(mappedBy = "roles")
  private Collection<AppUserEntity> appUsers;
  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(
      name = "roles_privileges",
      joinColumns = @JoinColumn(
          name = "id_role", referencedColumnName = "id_role"),
      inverseJoinColumns = @JoinColumn(
          name = "id_privilege", referencedColumnName = "id_privilege"))
  private Collection<Privilege> privileges;

  /*Getters and setters*/
  public Long getIdRole() {
    return idRole;
  }
  public void setIdRole(Long idRole) {
    this.idRole = idRole;
  }
  public String getRoleName() {
    return roleName;
  }
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  public Collection<AppUserEntity> getAppUsers() {
    return appUsers;
  }
  public void setAppUsers(Collection<AppUserEntity> appUsers) {
    this.appUsers = appUsers;
  }
  public Collection<Privilege> getPrivileges() {
    return privileges;
  }
  public void setPrivileges(Collection<Privilege> privileges) {
    this.privileges = privileges;
  }

}
