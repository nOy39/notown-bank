package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
}
