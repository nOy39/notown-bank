package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO продажи валюты
 * <code>String accountId</code>, счёт списания валюты.
 * <code>BigDecimal sum</code> сумма списания.
 */
@Data
public class TradingCurrencyRequest {
    @NotBlank
    private String accountId;
    @NotNull
    private BigDecimal sum;
}
