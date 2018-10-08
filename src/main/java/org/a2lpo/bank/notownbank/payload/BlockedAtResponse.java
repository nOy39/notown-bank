package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;

import java.util.Set;

@Data
public class BlockedAtResponse {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;

    public BlockedAtResponse(User admin) {
        this.id = admin.getId();
        this.username = admin.getUsername();
        this.email = admin.getEmail();
        this.roles = admin.getRoles();
    }
}
