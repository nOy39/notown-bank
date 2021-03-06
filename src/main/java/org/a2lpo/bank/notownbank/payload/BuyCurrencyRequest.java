package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 * DTO покупки валюты.
 * <code>CurrencyName currencyName</code> название покупаемой валюты.
 * <code>BigDecimal sum</code> сумма на которую покупается валюта.
 */
@Data
public class BuyCurrencyRequest {
    @NotNull
    private CurrencyName currencyName;
    @NotNull
    private BigDecimal sum;
}
