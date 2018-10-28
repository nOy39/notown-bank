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
public class HistoryListResponse {
    private Long id;
    private LocalDateTime transactionDate;
    private BigDecimal outgoingSum;
    private BigDecimal incomingSum;
    private String transactionClient;
    private String transactionAccount;

    public HistoryListResponse(History history) {
        this.transactionDate = history.getCreatedAt();
        this.id = history.getId();
        this.outgoingSum = history.getOutgoingSum();
        this.incomingSum = history.getIncomingSum();
        this.transactionAccount = history.getSecondaryAccount().getUniqCheckId();
        this.transactionClient = history
                .getSecondaryAccount()
                .getClient()
                .getFirstName() + " " +
                history.getSecondaryAccount()
                        .getClient()
                        .getLastName().substring(0,1).toUpperCase()+"...";
    }
}