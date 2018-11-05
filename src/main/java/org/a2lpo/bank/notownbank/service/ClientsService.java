package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.eav.Account;
import org.a2lpo.bank.notownbank.payload.ChangeCurrencyRequest;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientsService {
    public boolean checkSourceData(ChangeCurrencyRequest changeRequest,
                                   Optional<Account> optionalFrom,
                                   Optional<Account> optionalTo,
                                   UserPrincipal userPrincipal) {
        return false;
    }
}
