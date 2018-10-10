package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Role;

import java.util.List;

@Data
public class UserResponse {
    private String username;
    private String email;
    List<Role> roles;
    public UserResponse() {
    }

    public UserResponse(String username, String email, List roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserResponse{" + "'username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
