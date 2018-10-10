package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping(value = "/test")
    public String test() {
        return "Test api request";
    }

    @GetMapping(value = "/authMe")
    public String authMe(@CurrentUser UserPrincipal userPrincipal) {
        return userPrincipal.getUsername() + " is login.";
    }
}
