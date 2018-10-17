package org.a2lpo.bank.notownbank.model.accounts;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;

import javax.persistence.*;
import java.math.BigDecimal;

/**  Класс служебных счетов банка.
 */
@Data
@Entity
@Table(name = "service_account")
public class BankAccount extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountUUID;
    private BigDecimal sum;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private TypeAccount typeAccount;

    public BankAccount() {
    }

    public BankAccount(String accountUUID, BigDecimal sum) {
        this.accountUUID = accountUUID;
        this.sum = sum;
    }
}
