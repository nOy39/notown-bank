package org.a2lpo.bank.notownbank.model.message;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.a2lpo.bank.notownbank.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * таблица приходов денежных средств на счет клиента
 */
@Entity
@Table(name = "transfer_input_log")
@Data
public class HistoryInputPayment extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_account_id")
    private PersonalAccount byFrom;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_account_id")
    private PersonalAccount byTo;
    private BigDecimal sum;
    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private CurrencyName name;

    public HistoryInputPayment(@NotNull PersonalAccount byTo,
                               @NotNull PersonalAccount byFrom,
                               BigDecimal sum) {
        this.byFrom = byFrom;
        this.byTo = byTo;
        this.sum = sum;
        this.name = byTo.getCurrency().getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HistoryInputPayment that = (HistoryInputPayment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(byFrom, that.byFrom) &&
                Objects.equals(byTo, that.byTo) &&
                Objects.equals(sum, that.sum) &&
                name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, byFrom, byTo, sum, name);
    }
}
