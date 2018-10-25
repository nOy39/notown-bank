package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.Manager;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;

@Data
public class AuthenticationManagerResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private RoleName primaryRole;
    private User user;
    private Manager manager;

    public AuthenticationManagerResponse(String accessToken,
                                         RoleName primaryRole,
                                         Manager manager) {
        this.accessToken = accessToken;
        this.primaryRole = primaryRole;
        this.user = manager.getUser();
        this.manager = manager;
    }
}
