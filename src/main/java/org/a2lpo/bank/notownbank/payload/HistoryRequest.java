package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.message.logging.History;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO Истории операций по счету
 */
@Data
public class HistoryRequest {
    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal outgoingSum;
    private BigDecimal incomingSum;
    private PersonalAccount transactionAccount;
    private Client transactionClient;

    public HistoryRequest(History history) {
        this.createdAt = history.getCreatedAt();
        this.id = history.getId();
        this.outgoingSum = history.getOutgoingSum();
        this.incomingSum = history.getIncomingSum();
        this.transactionAccount = history.getSecondaryAccount();
        this.transactionClient = history.getSecondaryAccount().getClient();
    }
}
