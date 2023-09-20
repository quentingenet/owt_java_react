package com.quentingenet.openweighttracker;

import com.quentingenet.openweighttracker.entity.*;
import com.quentingenet.openweighttracker.repository.*;
import com.quentingenet.openweighttracker.service.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class SetupData implements CommandLineRunner {

  @Autowired
  PersonRepository personRepository;

  @Autowired
  AppUserRepository appUserRepository;

  @Autowired
  InitialDataRepository initialDataRepository;

  @Autowired
  AppUserServiceImpl appUserService;

  @Autowired
  MessageSource messages;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PrivilegeRepository privilegeRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    final String OWT_USER_ADMIN_PASSWORD = System.getenv("OWT_USER_ADMIN_PASSWORD");
    final String OWT_USER_ADMIN_EMAIL = System.getenv("OWT_USER_ADMIN_EMAIL");
    AppUserEntity appUserFinded= appUserRepository.findByAppUsername("kent1");

    if (appUserFinded == null){

      Privilege readPrivilege
          = createPrivilegeIfNotFound("READ_PRIVILEGE");
      Privilege writePrivilege
          = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

      List<Privilege> adminPrivileges = Arrays.asList(
          readPrivilege, writePrivilege);
      createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
      createRoleIfNotFound("ROLE_USER", adminPrivileges);


      Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");

      PersonEntity personToCreate = new PersonEntity();
      AppUserEntity appUser = new AppUserEntity();
      InitialDataEntity initialDataEntity = new InitialDataEntity();

      appUser.setAppUsername("kent1");
      appUser.setRoles(Arrays.asList(adminRole));
      appUser.setPassword(passwordEncoder.encode(OWT_USER_ADMIN_PASSWORD));
      appUser.setEmailUser(OWT_USER_ADMIN_EMAIL);

      appUserRepository.save(appUser);

      initialDataEntity.setRegisterUserDate(LocalDate.now());
      initialDataEntity.setYearBirth(1985);
      initialDataEntity.setGoalWeight(85d);
      initialDataEntity.setEuropeanUnitMeasure(true);
      initialDataEntity.setBodySize(184d);
      initialDataEntity.setMale(true);
      initialDataEntity.setAcceptedTerms(true);

      initialDataRepository.save(initialDataEntity);

      personToCreate.setUserInitData(initialDataEntity);

      personToCreate.setAppUserPerson(appUser);

      personRepository.save(personToCreate);

    }
  }

  Role createRoleIfNotFound(
      String name, Collection<Privilege> privileges) {

    Role role = roleRepository.findByRoleName(name);
    if (role == null) {
      role = new Role();
      role.setRoleName(name);
      role.setPrivileges(privileges);

      roleRepository.save(role);
    }
    return role;
  }

  Privilege createPrivilegeIfNotFound(String name) {

    Privilege privilege = privilegeRepository.findByName(name);
    if (privilege == null) {
      privilege = new Privilege();
      privilege.setName(name);
      privilegeRepository.save(privilege);
    }
    return privilege;
  }
}

