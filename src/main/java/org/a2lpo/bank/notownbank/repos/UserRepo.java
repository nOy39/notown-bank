package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
