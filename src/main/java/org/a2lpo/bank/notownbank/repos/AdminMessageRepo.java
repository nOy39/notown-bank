package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.message.AdminMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMessageRepo extends JpaRepository<AdminMessage, Long> {
}
