package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.Manager;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ManagerService {
    private final RoleService roleService;

    @Autowired
    public ManagerService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Метод блокирует менеджера, устанавливает ему статус isActive = false,
     * в поле BlockedAt записывает текущую дату и время в формате UTC,
     * в поле BlockedBy записывает пользователя который заблокировал менеджера,
     * затем удаляет пользователя из таблицы ролей.
     * @param manager менеджер которого необходимо удалить
     * @param user пользователь который удаляет менеджера из таблицы ролей
     */
    public void blockManager(Manager manager, User user) {
        manager.setActive(false);
        manager.setBlockedAt(LocalDateTime.now());
        manager.setBlockedBy(user);
        roleService.deleteRole(manager.getUser(), RoleName.ROLE_MANAGER);
    }
}
