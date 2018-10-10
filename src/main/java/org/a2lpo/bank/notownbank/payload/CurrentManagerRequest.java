package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

@Data
public class CurrentManagerRequest {
    private String firstName;
    private String lastName;
    private boolean isActive;
}
