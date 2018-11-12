package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.eav.Account;
import org.a2lpo.bank.notownbank.model.accounts.eav.Currency;
import org.a2lpo.bank.notownbank.model.accounts.eav.TypeAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO Детальная информация по счёту
 */
@Data
public class AccountInfoResponse {
    private final BigDecimal sum;
    private final LocalDateTime createdAt;
    private final String type;
    private final LocalDateTime updatedAt;
    private final String currency;
    private final String accountNumber;
    private final boolean isDefault;
    private final boolean isBlocked;
    private final Long createdBy;
    private final Long updatedBy;

    public AccountInfoResponse(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.currency = account.getCurrency().getCurrencyName();
        this.sum = account.getSum();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
        this.type = account.getType().getTypeName();
        this.isBlocked = account.isBlocked();
        this.isDefault = account.isDefault();
        this.createdBy = account.getCreatedBy();
        this.updatedBy = account.getUpdateBy();
    }
}
