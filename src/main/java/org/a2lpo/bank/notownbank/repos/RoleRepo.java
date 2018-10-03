package org.a2lpo.bank.notownbank.repos;

import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
