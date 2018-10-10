package org.a2lpo.bank.notownbank.model;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.audit.DateAudit;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "usr", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@Data
public class User extends DateAudit implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @Transient
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(@NotBlank @Size(max = 15) String username,
                @NotBlank @Size(max = 40) @Email String email,
                @NotBlank @Size(max = 100) String password,
                Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(@NotBlank @Size(max = 15) String username,
                @NotBlank @Size(max = 40) @Email String email,
                @NotBlank @Size(max = 100) String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }
}
