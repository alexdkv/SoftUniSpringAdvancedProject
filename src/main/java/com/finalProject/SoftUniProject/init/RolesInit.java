package com.finalProject.SoftUniProject.init;

import com.finalProject.SoftUniProject.model.entity.Role;
import com.finalProject.SoftUniProject.model.enums.UserRoleENUM;
import com.finalProject.SoftUniProject.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RolesInit implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RolesInit(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        long count =roleRepository.count();
        if (count == 0){
            List<Role> roles = new ArrayList<>();

            Arrays.stream(UserRoleENUM.values())
                    .forEach(roleName -> {
                        Role role = new Role();
                        role.setName(roleName);
                        roles.add(role);
                    });
            roleRepository.saveAll(roles);
        }
    }
}
