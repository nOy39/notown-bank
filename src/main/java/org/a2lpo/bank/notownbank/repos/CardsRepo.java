package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.accounts.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepo extends JpaRepository<Card, Long> {
}
