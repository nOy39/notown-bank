package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.message.logging.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepo extends JpaRepository<History, Long> {

    @Query(value = "select * from " +
            "history h left outer join " +
            "account a on h.main_account = a.id " +
            "where a.uniq_check_id = :uuid " +
            "order by h.created_at desc",
    nativeQuery = true)
    List<History> findAccountHistory(@Param("uuid") String uuid);
}
