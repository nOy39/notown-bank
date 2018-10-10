package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.exceptions.AppException;
import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.repos.RoleRepo;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public User addRole(User user, RoleName roleName) {
        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new AppException("Role not set"));
        user.getRoles().add(role);
        return user;
    }
}
