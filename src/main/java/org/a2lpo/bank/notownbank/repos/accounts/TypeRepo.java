package org.a2lpo.bank.notownbank.repos.accounts;

import org.a2lpo.bank.notownbank.model.accounts.eav.TypeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TypeRepo extends JpaRepository<TypeAccount, Long> {
    @Query(nativeQuery = true, value = "select * from eav_type et where et.type_name = :typeName")
    Optional<TypeAccount> findTypeAccountByName(@Param("typeName") String typeName);
}
