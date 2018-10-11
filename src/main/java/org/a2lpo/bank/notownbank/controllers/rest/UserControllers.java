package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/")
public class UserControllers {

    private final UserRepo userRepo;
    @Autowired
    public UserControllers(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/{username}")
    private User getUser (@PathVariable("username") String username) {
        System.out.println(username);
        return userRepo.findByUsername(username).get();
    }

}
