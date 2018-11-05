package org.a2lpo.bank.notownbank.model.accounts;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.accounts.eav.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Data
@Table(name = "cards",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "cardNumber"
                })
        })
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long cardNumber;

    @NotNull
    private String cvv;

    @NotNull
    private LocalDate created;

    @NotNull
    private LocalDate expired;

    @NotNull
    private String secretPin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account accountCard;

    public Card() {
    }

    public Card(Account accountCard) {
        this.cardNumber =  Long.valueOf("3011" + String.valueOf(
                Math.abs(new SecureRandom().nextLong())).substring(0, 12));
        this.created = LocalDate.now();
        this.expired = LocalDate.now().plusYears(4);
        this.secretPin = String.valueOf(Math.abs(new Random().nextInt())).substring(0,4);
        this.cvv = String.valueOf(Math.abs(new Random().nextInt())).substring(0,3);
        this.accountCard = accountCard;
    }
}
