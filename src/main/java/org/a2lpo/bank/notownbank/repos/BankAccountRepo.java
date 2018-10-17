package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.accounts.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {
    @Query(nativeQuery = true,
            value = "select * from service_account ba where ba.currency_id = :currency")
    Optional<BankAccount> findCurrencyBankAcount(@Param("currency") Long id);
}
