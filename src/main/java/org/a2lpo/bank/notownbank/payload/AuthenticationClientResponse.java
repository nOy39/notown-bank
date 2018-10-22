package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.audit.RoleName;

@Data
public class AuthenticationClientResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private RoleName primaryRole;
    private User user;
    private Client client;

    public AuthenticationClientResponse(String accessToken,
                                        RoleName primaryRole,
                                        Client client) {
        this.accessToken = accessToken;
        this.primaryRole = primaryRole;
        this.user = client.getUser();
        this.client = client;
    }
}
