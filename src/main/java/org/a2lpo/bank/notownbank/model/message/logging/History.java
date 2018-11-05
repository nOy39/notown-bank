package org.a2lpo.bank.notownbank.model.message.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Data;
import org.a2lpo.bank.notownbank.model.User;
import org.a2lpo.bank.notownbank.model.accounts.eav.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Data
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private BigDecimal outgoingSum;
    private BigDecimal incomingSum;
    private LocalDateTime createdAt;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_account")
    private Account mainAccount;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "secondary_account")
    private Account secondaryAccount;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public History(@NotNull Account mainAccount,
                   @NotNull Account secondaryAccount,
                   BigDecimal outgoingSum,
                   BigDecimal incomingSum) {
        this.outgoingSum = outgoingSum;
        this.incomingSum = incomingSum;
        this.mainAccount = mainAccount;
        this.secondaryAccount = secondaryAccount;
        this.createdAt = LocalDateTime.now();
        this.user = mainAccount.getAccountHolder().getUser();
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
