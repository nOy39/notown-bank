package org.a2lpo.bank.notownbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.a2lpo.bank.notownbank.model.accounts.eav.Account;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/**
 * Клиентская таблица
 */
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
    private boolean commerceClient;
    @NotNull
    private LocalDate dateOfBirth;

    @JsonIgnore
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "accountHolder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Account> clientAccounts;

    public Client(String firstName,
                  String lastName,
                  Long phone,
                  LocalDate dateOfBirth,
                  User user) {
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        this.phone = phone;
        this.email = user.getEmail();
        this.isActive = false;
        this.dateOfBirth = dateOfBirth;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return isActive == client.isActive &&
                commerceClient == client.commerceClient &&
                Objects.equal(id, client.id) &&
                Objects.equal(firstName, client.firstName) &&
                Objects.equal(lastName, client.lastName) &&
                Objects.equal(phone, client.phone) &&
                Objects.equal(email, client.email) &&
                Objects.equal(dateOfBirth, client.dateOfBirth) &&
                Objects.equal(user, client.user) &&
                Objects.equal(clientAccounts, client.clientAccounts);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id);
    }
}
