package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.Manager;
import org.a2lpo.bank.notownbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ManagerRepo extends JpaRepository<Manager, Long> {

    Optional<Manager> findByUniqId(String uuid);

    @Query(value = "select m from Manager m where m.uniqId = ?1 or m.id = ?2 and m.isActive = true")
    Optional<Manager> searchActiveManagerByUniqId(String uuid, Long id);

    List<Manager> findAllByFirstNameOrLastName(String firstName, String lastName);

    Optional<Manager> findByUser(User user);
}
