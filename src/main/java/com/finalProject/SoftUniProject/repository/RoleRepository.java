package com.finalProject.SoftUniProject.repository;

import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoleENUM name);
}
