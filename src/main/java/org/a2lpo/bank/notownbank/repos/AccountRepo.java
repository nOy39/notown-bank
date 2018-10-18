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

    /**
     * Поиск всех счетов по USER_ID
     * @param id user_id
     * @return List<PersonalAccount>
     */
    List<PersonalAccount> findAllByClient_User_Id(Long id);

    /**
     * метод поиска активных(не заблокированных) счетов по номеру счета, используется для отправки/оплаты/переводов денежных средст
     * @param uuid номер счёта
     * @return Optional<PersonalAccount>
     */
    @Query(nativeQuery = true,
            value = "select * from account a " +
                    "where a.is_blocked = false " +
                    "and a.uniq_check_id = :uuid")
    Optional<PersonalAccount> findActiveAccountByUUID(@Param("uuid") String uuid);

    /**
     * метод поиска по номеру счета, используется для получения денежных средств
     * @param uuid номер счёта
     * @return Optional<PersonalAccount>
     */
    @Query(value = "select a from PersonalAccount a where a.uniqCheckId = ?1")
    Optional<PersonalAccount> findAccountByUUID(String uuid);

    /**
     * метод поиска дефолтных счетов, используются для дефолтных приходов денежных средств(продажа валюты, онлайн переводы)
     * @param clientId - id клиента из таблицы клиентов
     * @param currency - валюта к которой принадлежит дефолтный счет
     * @return Optional<PersonalAccount>
     */
    @Query(nativeQuery = true,
    value = "select * from account a " +
            "where a.client_id = :clientId " +
            "and a.is_default = true " +
            "and a.currency_id = :currency " +
            "limit 1")
    Optional<PersonalAccount> findDefaultAccounts(@Param("clientId")Long clientId, @Param("currency")Long currency);

    /**
     * метод поиска не заблокированного дефолтного счета, используется для деволных расчетов с этого счета(покупка валюты, онлайн покупки)
     * @param clientId - id клиента из таблицы клиентов
     * @param currency - валюта к которой принадлежит дефолтный счет
     * @return Optional<PersonalAccount>
     */
    @Query(nativeQuery = true,
    value = "select * from account a " +
            "where a.client_id = :clientId " +
            "and a.is_default = true " +
            "and a.currency_id = :currency " +
            "and a.is_blocked = false " +
            "limit 1")
    Optional<PersonalAccount> findActiveDefaultAccounts(@Param("clientId")Long clientId, @Param("currency")Long currency);
}
