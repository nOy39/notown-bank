package org.a2lpo.bank.notownbank.service;

import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.payload.ChangeCurrencyRequest;
import org.a2lpo.bank.notownbank.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientsService {
    public boolean checkSourceData(ChangeCurrencyRequest changeRequest,
                                   Optional<PersonalAccount> optionalFrom,
                                   Optional<PersonalAccount> optionalTo,
                                   UserPrincipal userPrincipal) {
        return false;
    }
}
