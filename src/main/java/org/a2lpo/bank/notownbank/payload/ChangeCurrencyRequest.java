package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeCurrencyRequest {
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal sum;
}
