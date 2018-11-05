package org.a2lpo.bank.notownbank.model.accounts.eav;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Data;
import org.a2lpo.bank.notownbank.model.Client;
import org.a2lpo.bank.notownbank.model.accounts.Card;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

/**
 * <b>Класс персонального счета клиента.</b><br>
 * При создании объекта этого класса в конструтор передается
 * объекты <b>Client</b>,<b>Currency</b>,<b>TypeAccount</b>,
 * уникальный счет клиента (accountNumber) генерируется внутри конструктора,
 * баланс счёта (sum) устанавливается в 0.00, состояние счета isBlocked
 * в состояние true.
 */
@Entity
@Table(name = "account", uniqueConstraints = {
        @UniqueConstraint(columnNames = "accountNumber")
})
@Data
public class Account extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal sum;
    private boolean isBlocked;
    private boolean isDefault;

    @NotNull
    private String accountNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private TypeAccount type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sub_type_id")
    private SubTypeAccount subType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    private String secretCode;

    private String bankCode;

    private String personalCode;

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client accountHolder;

    @OneToMany(mappedBy = "accountCard",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Card> cards;

    public Account() {
    }

    public Account(Client client, TypeAccount typeAccount, SubTypeAccount subTypeAccount,
                   Currency currency, Long count) {
        this.bankCode = "0007";
        this.secretCode = "7";
        this.personalCode = getUniqNumber(++count);
        this.type = typeAccount;
        this.subType = subTypeAccount;
        this.currency = currency;
        this.accountHolder = client;
        this.accountNumber = String.valueOf(accountBuilder(
                typeAccount.getTypeCode(),
                subTypeAccount.getSubTypeCode(),
                currency.getCurrencyCode(),
                personalCode));
    }

    private StringBuilder accountBuilder(String typeCode, String subTypeCode, String currencyCode, String personalCode) {

        StringBuilder sb = new StringBuilder();
        return sb.append(typeCode)
                .append(subTypeCode)
                .append(currencyCode)
                .append(getSecretCode())
                .append(getBankCode())
                .append(personalCode);
    }

    private String getUniqNumber(Long count) {
        StringBuilder personalAcc = new StringBuilder(String.valueOf(count));
        while (personalAcc.length() < 9) {
            personalAcc.insert(0, "0");
        }
        return String.valueOf(personalAcc);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, accountNumber);
    }
}
