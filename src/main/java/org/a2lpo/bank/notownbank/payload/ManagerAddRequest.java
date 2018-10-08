package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ManagerAddRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
