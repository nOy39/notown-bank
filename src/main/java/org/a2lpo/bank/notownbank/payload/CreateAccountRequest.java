package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DTO запрос на создание нового счёта
 */
@Data
public class CreateAccountRequest {
    @NotNull
    private String currencyName;
    @NotNull
    private String typeAccount;
    @NotNull
    private String subTypeAccount;
    private boolean isDefault;
}
