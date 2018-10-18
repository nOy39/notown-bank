package org.a2lpo.bank.notownbank.model.accounts;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

/**
 *<b>Класс персонального счета клиента.</b><br>
 *     При создании объекта этого класса в конструтор передается
 *     объекты <b>Client</b>,<b>Currency</b>,<b>TypeAccount</b>,
 *     уникальный счет клиента (uniqCheckId) генерируется внутри конструктора,
 *     баланс счёта (sum) устанавливается в 0.00, состояние счета isBlocked
 *     в состояние true.
 */
@Entity
@Table(name = "account")
@Data
public class PersonalAccount extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal sum;
    @NotNull
    private String uniqCheckId;
    private boolean isBlocked;
    private boolean isDefault;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private TypeAccount typeAccount;

    public PersonalAccount() {
    }

    public PersonalAccount(@NotNull Client client,
                           Currency currency,
                           TypeAccount typeAccount) {
        this.sum = BigDecimal.valueOf(0.00);
        this.uniqCheckId = UUID.randomUUID().toString();
        this.client = client;
        this.currency = currency;
        this.typeAccount = typeAccount;
        this.isBlocked = true;
    }

    @Override
    public String toString() {
        return "PersonalAccount{" +
                "id=" + id +
                ", sum=" + sum +
                ", uniqCheckId='" + uniqCheckId + '\'' +
                ", isBlocked=" + isBlocked +
                ", isDefault=" + isDefault +
                ", client=" + client +
                ", currency=" + currency +
                ", typeAccount=" + typeAccount +
                '}';
    }
}
