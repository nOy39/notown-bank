package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    List<Account> findAllByClient_User_Id(Long id);

    @Query(value = "select a from Account a where a.uniqCheckId = ?1")
    Optional<Account> findCheckByUniqID(String uuid);
}
