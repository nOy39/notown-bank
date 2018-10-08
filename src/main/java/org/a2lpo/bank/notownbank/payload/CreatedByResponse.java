package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Role;
import org.a2lpo.bank.notownbank.model.User;

import java.util.Set;

@Data
public class CreatedByResponse {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;

    public CreatedByResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

    public CreatedByResponse(Long id,
                             String username,
                             String email,
                             Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
