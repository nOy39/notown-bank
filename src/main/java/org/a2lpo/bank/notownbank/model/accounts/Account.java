package org.a2lpo.bank.notownbank.model.accounts;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
@Data
public class Account extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal sum;
    @NotNull
    private String uniqCheckId;
    private boolean isBlocked;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id")
    private Currency currencyName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private TypeAccount typeAccount;

    public Account(@NotNull Client client,
                   Currency currencyName,
                   TypeAccount typeAccount) {
        this.sum = BigDecimal.valueOf(0.00);
        this.uniqCheckId = UUID.randomUUID().toString();
        this.client = client;
        this.currencyName = currencyName;
        this.typeAccount = typeAccount;
    }
}
