package com.quentingenet.openweighttracker.repository;

import com.quentingenet.openweighttracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

  Role findByRoleName(String roleName);
}
