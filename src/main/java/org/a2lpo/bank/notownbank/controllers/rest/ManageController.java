package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/control")
public class ManageController {

    @GetMapping
    private void checkClient() {
//        boolean isFind =
//                user.getRoles()
//                        .stream()
//                        .map(Role::getName)
//                        .anyMatch(n -> n == RoleName.ROLE_CLIENT);
    }
}
