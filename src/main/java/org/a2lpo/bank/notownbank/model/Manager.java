package org.a2lpo.bank.notownbank.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;

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
@EqualsAndHashCode(callSuper = false)
public class Manager extends UserDateAudit {
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
    private String uniqId;
    private boolean isActive;
    private LocalDateTime blockedAt;
    private String personalPage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blocked_by")
    private User blockedBy;
    public Manager(@NotBlank String firstName,
                   @NotBlank String lastName,
                   @NotNull User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.user = user;
        this.uniqId = UUID.randomUUID().toString();
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public void setBlockedAt(LocalDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    public String getPersonalPage() {
        return personalPage;
    }

    public void setPersonalPage(String personalPage) {
        this.personalPage = personalPage;
    }

    public User getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(User blockedBy) {
        this.blockedBy = blockedBy;
    }
}
