package org.a2lpo.bank.notownbank.repos.accounts;

import org.a2lpo.bank.notownbank.model.accounts.eav.SubTypeAccount;
import org.a2lpo.bank.notownbank.model.accounts.eav.TypeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubTypeRepo extends JpaRepository<SubTypeAccount, Long> {

    /**
     * Поиск доступных дополнительных счетов по типу счёта.
     * @param typeAccount тип счёта
     * @return
     */
    List<SubTypeAccount> findAllByType(TypeAccount typeAccount);

    Optional<SubTypeAccount> findBySubTypeName(String name);
}
