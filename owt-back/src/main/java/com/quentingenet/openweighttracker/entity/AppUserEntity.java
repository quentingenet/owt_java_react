package com.quentingenet.openweighttracker.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "app_users")
public class AppUserEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long idUser;

	@NotBlank
	@Size(min = 3, max = 50)
	@Column(name = "appUsername", nullable = false, unique = true, length = 50)
	private String appUsername;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "id_user", referencedColumnName = "id_user"),
			inverseJoinColumns = @JoinColumn(
					name = "id_role", referencedColumnName = "id_role"))
	private Collection<Role> roles;

	@NotBlank
	@Size(min = 4, max = 87)
	@Column(name = "email_user", nullable = false, unique = true, length = 50)
	private String emailUser;

	@NotBlank
	@Size(min = 4, max = 249)
	@Column(name = "password", nullable = false, length = 250)
	private String password;


	/* Getters and setters */
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getAppUsername() {
		return appUsername;
	}

	public void setAppUsername(String appUsername) {
		this.appUsername = appUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}


}
