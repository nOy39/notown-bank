package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.accounts.Currency;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {
    Optional<Currency> findByName(CurrencyName currencyName);
}
