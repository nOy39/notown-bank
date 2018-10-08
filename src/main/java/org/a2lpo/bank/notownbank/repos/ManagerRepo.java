package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepo extends JpaRepository<Manager, Long> {
    Optional<Manager> findByUniqId(String uuid);
}
