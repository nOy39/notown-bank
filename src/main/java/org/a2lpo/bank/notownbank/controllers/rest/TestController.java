package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    RoleService roleService;
    @Autowired
    UserRepo userRepo;

    @GetMapping(value = "/test")
    public String test() {
        return "Test api request";
    }

    @GetMapping(value = "/authMe")
    public String authMe(@CurrentUser UserPrincipal userPrincipal) {
        return userPrincipal.getUsername() + " is login.";
    }
}
