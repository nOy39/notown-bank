package org.a2lpo.bank.notownbank.model.message;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.accounts.CurrencyName;
import org.a2lpo.bank.notownbank.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transfer_output_log")
@Data
public class HistoryOutputPayment extends DateAudit {
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
    private BigDecimal commission;
    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private CurrencyName name;

    public HistoryOutputPayment(@NotNull PersonalAccount byFrom,
                                @NotNull PersonalAccount byTo,
                                BigDecimal sum,
                                BigDecimal commission) {
        this.byFrom = byFrom;
        this.byTo = byTo;
        this.sum = sum;
        this.commission = commission;
        this.name = byFrom.getCurrency().getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HistoryOutputPayment that = (HistoryOutputPayment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(byFrom, that.byFrom) &&
                Objects.equals(byTo, that.byTo) &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(commission, that.commission) &&
                name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, byFrom, byTo, sum, commission, name);
    }
}
