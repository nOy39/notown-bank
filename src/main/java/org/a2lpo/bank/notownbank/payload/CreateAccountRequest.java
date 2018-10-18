package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.a2lpo.bank.notownbank.model.accounts.TypeAccountName;

import javax.validation.constraints.NotNull;

/**
 * DTO запрос на создание нового счёта
 */
@Data
public class CreateAccountRequest {
    @NotNull
    private CurrencyName currencyName;
    @NotNull
    private TypeAccountName typeAccountName;
    private boolean isDefault;
}
