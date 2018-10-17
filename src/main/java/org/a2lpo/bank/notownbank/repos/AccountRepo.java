package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<PersonalAccount, Long> {

    List<PersonalAccount> findAllByClient_User_Id(Long id);

    @Query(value = "select a from PersonalAccount a where a.uniqCheckId = ?1")
    Optional<PersonalAccount> findCheckByUniqID(String uuid);

    @Query(nativeQuery = true,
    value = "select * from account a where a.client_id = :clientId and a.is_default = true and a.currency_id = :currency limit 1")
    Optional<PersonalAccount> findDefaultAccounts(@Param("clientId")Long clientId, @Param("currency")Long currency);
}
