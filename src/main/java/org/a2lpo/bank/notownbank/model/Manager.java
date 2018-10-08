package org.a2lpo.bank.notownbank.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "manager", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "user_id"
        }),
        @UniqueConstraint(columnNames = {
                "uniqId"
        })
})
@Data
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private String uniqId;
    private boolean isActive;
    private LocalDateTime blocked;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private User createdBy;

    public Manager(@NotBlank String firstName,
                   @NotBlank String lastName,
                   @NotNull User user,
                   @NotNull User createdBy) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
        this.uniqId = UUID.randomUUID().toString();
        this.createdBy = createdBy;
        this.created = LocalDateTime.now();
        this.isActive = true;
    }
}
