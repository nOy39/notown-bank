package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.accounts.TypeAccount;
import org.a2lpo.bank.notownbank.model.accounts.TypeAccountName;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.AccessType;
import java.util.Optional;

public interface TypeRepo extends JpaRepository<TypeAccount, Long> {
    Optional<TypeAccount> findByType(TypeAccountName typeName);
}
