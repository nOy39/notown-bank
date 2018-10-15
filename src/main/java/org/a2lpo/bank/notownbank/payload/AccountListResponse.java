package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.a2lpo.bank.notownbank.model.accounts.TypeAccountName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountListResponse {
    private String accountId;
    private BigDecimal sum;
    private LocalDateTime updatedAt;
    private CurrencyName currency;
    private TypeAccountName type;

    public AccountListResponse(String accountId,
                               BigDecimal sum,
                               LocalDateTime updatedAt,
                               CurrencyName currency,
                               TypeAccountName type) {
        this.accountId = accountId;
        this.sum = sum;
        this.updatedAt = updatedAt;
        this.currency = currency;
        this.type = type;
    }
}
