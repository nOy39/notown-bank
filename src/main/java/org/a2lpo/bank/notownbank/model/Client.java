package org.a2lpo.bank.notownbank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime dateOfRegistration;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

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
        this.dateOfRegistration = LocalDateTime.now();
        this.user = user;
    }
}
