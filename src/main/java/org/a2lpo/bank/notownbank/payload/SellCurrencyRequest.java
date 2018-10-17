package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import java.math.BigDecimal;
//todo описание класа
@Data
public class SellCurrencyRequest {
    private String fromAccountId;
    private BigDecimal sum;
}
