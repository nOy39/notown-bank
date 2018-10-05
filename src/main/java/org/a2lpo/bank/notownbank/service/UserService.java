package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Optional<User> extractUser(UserPrincipal userPrincipal) {
        return userRepo.findByUsername(userPrincipal.getUsername());
    }
}
