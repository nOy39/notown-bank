package org.a2lpo.bank.notownbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.a2lpo.bank.notownbank.model.accounts.Card;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "clients", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "phone"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        }),
        @UniqueConstraint(columnNames = {
                "user_id"
        })
})
@Data
@EqualsAndHashCode(callSuper = false)
public class Client extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Long phone;
    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;
    private boolean isActive;
    private LocalDate dateOfBirth;
    @JsonIgnore
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "cardHolder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Card> cards;

    public Client() {
    }

    public Client(@NotBlank String firstName,
                  @NotBlank String lastName,
                  @NotBlank Long phone,
                  LocalDate dateOfBirth,
                  @NotNull User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = user.getEmail();
        this.isActive = false;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

    public Client(@NotBlank String firstName,
                  @NotBlank String lastName,
                  @NotNull Long phone,
                  @NotBlank @Size(max = 40) @Email String email,
                  boolean isActive,
                  LocalDate dateOfBirth,
                  @NotNull User user,
                  Set<Card> cards) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
        this.cards = cards;
    }
}
