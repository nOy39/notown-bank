package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.accounts.eav.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findAllByAccountHolder_User_Id(Long id);
    /**
     * метод поиска активных(не заблокированных) счетов по номеру счета, используется для отправки/оплаты/переводов денежных средст
     * @param uuid номер счёта
     * @return Optional<Account>
     */
    @Query(nativeQuery = true,
            value = "select * from account a " +
                    "where a.is_blocked = false " +
                    "and a.uniq_check_id = :uuid")
    Optional<Account> findActiveAccountByUUID(@Param("uuid") String uuid);

    /**
     * метод поиска по номеру счета, используется для получения денежных средств
     * @param uuid номер счёта
     * @return Optional<Account>
     */
    @Query(nativeQuery = true, value = "select * from account ac where ac.account_number = :accNumber")
    Optional<Account> findAccountByUUID(@Param("accNumber") String accNumber);

    /**
     * метод поиска дефолтных счетов, используются для дефолтных приходов денежных средств(продажа валюты, онлайн переводы)
     * @param clientId - id клиента из таблицы клиентов
     * @param currency - валюта к которой принадлежит дефолтный счет
     * @return Optional<Account>
     */
    @Query(nativeQuery = true,
    value = "select * from account a " +
            "where a.client_id = :clientId " +
            "and a.is_default = true " +
            "and a.currency_id = :currency " +
            "limit 1")
    Optional<Account> findDefaultAccounts(@Param("clientId")Long clientId, @Param("currency")Long currency);

    /**
     * метод поиска не заблокированного дефолтного счета, используется для деволных расчетов с этого счета(покупка валюты, онлайн покупки)
     * @param clientId - id клиента из таблицы клиентов
     * @param currency - валюта к которой принадлежит дефолтный счет
     * @return Optional<Account>
     */
    @Query(nativeQuery = true,
    value = "select * from account a " +
            "where a.client_id = :clientId " +
            "and a.is_default = true " +
            "and a.currency_id = :currency " +
            "and a.is_blocked = false " +
            "limit 1")
    Optional<Account> findActiveDefaultAccounts(@Param("clientId")Long clientId, @Param("currency")Long currency);

    @Query(nativeQuery = true,
    value = "select * from account a " +
            "left outer join clients c2 " +
            "on a.client_id = c2.id " +
            "where c2.user_id = :userId " +
            "and a.uniq_check_id = :accountNumber")
    Optional<Account> findPersonalAccountByUserIdAndByAccountNumber(@Param("userId")Long userId,
                                                                    @Param("accountNumber")String accNumber);

    @Query(nativeQuery = true, value = "select count(id) from account ac where ac.type_id = :typeId and ac.sub_type_id = :subTypeId")
    Long countAccount(@Param("typeId")Long typeId, @Param("subTypeId") Long subTypeId);
}
