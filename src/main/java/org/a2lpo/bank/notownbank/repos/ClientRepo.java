package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {
    Optional<Client> findByUser(User user);

    @Query(nativeQuery = true,
            value = "select * from clients where user_id = ?1")
    Optional<Client> findByUserId(Long id);
}
