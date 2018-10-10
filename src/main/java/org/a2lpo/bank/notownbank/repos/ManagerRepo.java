package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.Manager;
import org.a2lpo.bank.notownbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface ManagerRepo extends JpaRepository<Manager, Long> {
    Optional<Manager> findByUniqId(String uuid);

    List<Manager> findAllByFirstNameOrLastName(String firstName, String lastName);
    List<Manager> findAllByActiveFalse();
    List<Manager> findAllByActiveTrue();
    Optional<Manager> findByUser(User user);
}
