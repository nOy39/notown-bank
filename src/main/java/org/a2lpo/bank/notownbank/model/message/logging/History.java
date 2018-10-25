package org.a2lpo.bank.notownbank.model.message.logging;

import com.google.common.base.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.a2lpo.bank.notownbank.model.accounts.PersonalAccount;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "history")
@Data
public class History extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal outgoingSum;
    private BigDecimal incomingSum;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_account")
    private PersonalAccount mainAccount;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secondary_account")
    private PersonalAccount secondaryAccount;

    public History(@NotNull PersonalAccount mainAccount,
                   @NotNull PersonalAccount secondaryAccount,
                   BigDecimal outgoingSum,
                   BigDecimal incomingSum) {
        this.outgoingSum = outgoingSum;
        this.incomingSum = incomingSum;
        this.mainAccount = mainAccount;
        this.secondaryAccount = secondaryAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        History history = (History) o;
        return Objects.equal(id, history.id) &&
                Objects.equal(outgoingSum, history.outgoingSum) &&
                Objects.equal(incomingSum, history.incomingSum) &&
                Objects.equal(mainAccount, history.mainAccount) &&
                Objects.equal(secondaryAccount, history.secondaryAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, outgoingSum, incomingSum, mainAccount, secondaryAccount);
    }
}
