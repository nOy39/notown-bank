package org.a2lpo.bank.notownbank.repos.accounts;

import org.a2lpo.bank.notownbank.model.accounts.eav.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CurrencyRepo extends JpaRepository<Currency, Long> {

    @Query(nativeQuery = true,
    value = "select * from eav_currency ec where ec.currency_name = :currency")
    Optional<Currency> findCurrencyByName(@Param("currency") String currencyName);
}
