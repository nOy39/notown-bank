package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.exceptions.AppException;
import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.repos.RoleRepo;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {

    private final RoleRepo roleRepo;

    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    /**
     * метод добавление ролей юзеру
     * @param user юзер которому добавляем роль
     * @param roleName Роль которую необходимо добавить
     * @return возвращаем user
     */
    public User addRole(User user, RoleName roleName) {
        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new AppException("Role not set"));
        user.getRoles().add(role);
        return user;
    }

    public User deleteRole(User user, RoleName roleName) {
        Set<Role> roles = user.getRoles();
        Role role = roleRepo.findByName(roleName)
                .orElseThrow(() -> new AppException("Role not found"));
        roles.remove(role);
        return user;
    }
}
