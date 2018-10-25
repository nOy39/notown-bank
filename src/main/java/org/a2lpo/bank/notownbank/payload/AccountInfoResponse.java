package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.accounts.TypeAccountName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO Детальная информация по счёту
 */
@Data
public class AccountInfoResponse {
    private final BigDecimal sum;
    private final LocalDateTime createdAt;
    private final TypeAccountName type;
    private final LocalDateTime updatedAt;
    private final CurrencyName currency;
    private final String uniqCheckId;
    private final boolean isDefault;
    private final boolean isBlocked;
    private final Long createdBy;
    private final Long updatedBy;

    public AccountInfoResponse(String uniqCheckId,
                               BigDecimal sum,
                               CurrencyName currency,
                               TypeAccountName type,
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

    public AccountInfoResponse(PersonalAccount personalAccount) {
        this.uniqCheckId = personalAccount.getUniqCheckId();
        this.currency = personalAccount.getCurrency().getName();
        this.sum = personalAccount.getSum();
        this.createdAt = personalAccount.getCreatedAt();
        this.updatedAt = personalAccount.getUpdatedAt();
        this.type = personalAccount.getTypeAccount().getType();
        this.isBlocked = personalAccount.isBlocked();
        this.isDefault = personalAccount.isDefault();
        this.createdBy = personalAccount.getCreatedBy();
        this.updatedBy = personalAccount.getUpdateBy();
    }
}
