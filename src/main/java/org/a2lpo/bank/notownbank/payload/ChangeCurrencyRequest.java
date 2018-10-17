package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO запрос на обмен валюты.
 * fromAccountId - счет с которого списываем деньги
 * toAccountId - счет на который зачисляются деньги
 * sum - сумма списания
 */
@Data
public class ChangeCurrencyRequest {
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal sum;
}
