package com.quentingenet.openweighttracker.security.configuration;

import com.quentingenet.openweighttracker.entity.AppUserEntity;
import com.quentingenet.openweighttracker.entity.Privilege;
import com.quentingenet.openweighttracker.entity.Role;
import com.quentingenet.openweighttracker.repository.AppUserRepository;
import com.quentingenet.openweighttracker.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Service
public class MyUserDetailService implements UserDetailsService  {

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String appUsername) throws UsernameNotFoundException {
		AppUserEntity appUser = appUserRepository.findByAppUsername(appUsername);
		if (appUser == null) {
			return new org.springframework.security.core.userdetails.User(
					" ", " ", false, true, true, true,
					getAuthorities(Arrays.asList(
							roleRepository.findByRoleName("ROLE_USER"))));
		}
		return new org.springframework.security.core.userdetails.User(
				appUser.getAppUsername(), appUser.getPassword(), true, true, true,
				true, getAuthorities(appUser.getRoles()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(
			Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		List<String> privileges = new ArrayList<>();
		List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			privileges.add(role.getRoleName());
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}



}
