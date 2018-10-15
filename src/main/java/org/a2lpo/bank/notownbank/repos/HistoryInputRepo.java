package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.message.HistoryInputPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryInputRepo extends JpaRepository<HistoryInputPayment, Long> {
}
