package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.message.logging.Status;
import org.a2lpo.bank.notownbank.model.message.logging.StatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusLogRepo extends JpaRepository<StatusLog, Long> {
    Optional<StatusLog> findByStatus(Status statusLog);
}
