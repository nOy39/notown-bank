package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
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
    private final TypeAccount type;
    private final LocalDateTime updatedAt;
    private final Currency currency;
    private final String uniqCheckId;
    private final boolean isDefault;
    private final boolean isBlocked;
    private final Long createdBy;
    private final Long updatedBy;

    public AccountInfoResponse(String uniqCheckId,
                               BigDecimal sum,
                               Currency currency,
                               TypeAccount type,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt,
                               Long createdBy,
                               Long updatedBy,
                               boolean isDefault,
                               boolean isBlocked) {
        this.sum = sum;
        this.createdAt = createdAt;
        this.type = type;
        this.updatedAt = updatedAt;
        this.currency = currency;
        this.uniqCheckId = uniqCheckId;
        this.isDefault = isDefault;
        this.isBlocked = isBlocked;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

//    public AccountInfoResponse(Account account) {
//        this.uniqCheckId = account.getAccountNumber();
//        this.currency = account.getCurrency().getName();
//        this.sum = account.getSum();
//        this.createdAt = account.getCreatedAt();
//        this.updatedAt = account.getUpdatedAt();
//        this.type = account.getTypeAccount().getType();
//        this.isBlocked = account.isBlocked();
//        this.isDefault = account.isDefault();
//        this.createdBy = account.getCreatedBy();
//        this.updatedBy = account.getUpdateBy();
//    }
}
