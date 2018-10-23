package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    RoleService roleService;
    @Autowired
    UserRepo userRepo;

    @GetMapping(value = "/test")
    public String test(@AuthenticationPrincipal User user) {
        User user1 = user;
        return "Test api request";
    }

    @GetMapping(value = "/authMe")
    public String authMe(@CurrentUser UserPrincipal userPrincipal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userPrincipal.getUsername() + " is login.";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable("id") User user) {
        try {
            userRepo.delete(user);
            return "Done";
        } catch (Exception e) {
            return e.toString();
        }
    }
}
