package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.message.logging.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

public interface HistoryRepo extends JpaRepository<History, Long> {
    /**
     * @param uuid
     * @return
     */
    @Query(value = "select * from " +
            "history h left outer join " +
            "account a on h.main_account = a.id " +
            "where a.uniq_check_id = :uuid " +
            "order by h.created_at desc",
            nativeQuery = true)
    List<History> findAccountHistory(@Param("uuid") String uuid);

    /**
     * Поиск записей по счёту за период
     *
     * @param uuid      - уникальный номер счета
     * @param firstDate - начало периода
     * @param lastDate  - конец периода
     * @return
     */
    @Query(nativeQuery = true,
            value = "select * from  history h " +
                        "left outer join  account a on h.main_account = a.id  where a.account_number = :uuid " +
                            "and h.user_id = :userId " +
                            "and h.created_at > :firstDate " +
                            "and h.created_at < :lastDate " +
                        "order by h.created_at desc")
    List<History> findAccountHistoryByPeriod(@Param("userId") Long userId,
                                                @Param("uuid") String uuid,
                                                @Param("firstDate") LocalDateTime firstDate,
                                                @Param("lastDate") LocalDateTime lastDate);

    @Query(nativeQuery = true,
    value = "select * from history h where h.user_id = :userId order by created_at desc limit :lmt")
    List<History> findLastTenTransaction(@Param("userId") Long userId, @Param("lmt") int limit);
}
