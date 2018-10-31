package org.a2lpo.bank.notownbank.model.accounts;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    private Long cardNumber;
    private String cvv;
    @NotNull
    private LocalDate created;
    @NotNull
    private LocalDate expired;
    private String secretPin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private PersonalAccount accountCard;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client cardHolder;

    public Card() {
    }

    public Card(PersonalAccount accountCard) {
        this.cardNumber = Long.valueOf("3011" +
                String.valueOf(Math.abs(
                        new SecureRandom().nextLong()))
                        .substring(0, 12)
        );
        this.created = LocalDate.now();
        this.expired = LocalDate.now().plusYears(4);
        this.secretPin = String.valueOf(Math.abs(new Random().nextInt())).substring(0,4);
        this.cvv = String.valueOf(Math.abs(new Random().nextInt())).substring(0,3);
        this.accountCard = accountCard;
        this.cardHolder = accountCard.getClient();
    }
}
