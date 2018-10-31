package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OpenCardRequest {
    @NotNull
    private String accountNumber;

    public OpenCardRequest() {
    }
}
