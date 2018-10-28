package org.a2lpo.bank.notownbank.controllers.rest;

import org.a2lpo.bank.notownbank.model.message.logging.History;
import org.a2lpo.bank.notownbank.payload.HistoryRequest;
import org.a2lpo.bank.notownbank.repos.HistoryRepo;
import org.a2lpo.bank.notownbank.repos.UserRepo;
import org.a2lpo.bank.notownbank.security.CurrentUser;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.a2lpo.bank.notownbank.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    RoleService roleService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    HistoryRepo historyRepo;

    @GetMapping()
    @RequestMapping(value = "/authMe")
    public String authMe(@CurrentUser UserPrincipal userPrincipal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userPrincipal.getUsername() + " is login.";
    }

    @GetMapping
    public String noAuthGet() {
        return "Server worked";
    }

    @PostMapping()
    @RequestMapping(value = "/period")
    public List<History> periodHistory(@RequestBody HistoryRequest historyRequest,
                                          @CurrentUser UserPrincipal userPrincipal) {
        List<History> accountHistoryByPeriod = historyRepo.findAccountHistoryByPeriod(userPrincipal.getId(),
                historyRequest.getUniqCheckId(),
                historyRequest.getFirstDate(),
                historyRequest.getLastDate());

        return accountHistoryByPeriod;
    }
}
