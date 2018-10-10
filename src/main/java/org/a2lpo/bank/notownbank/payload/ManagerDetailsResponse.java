package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Manager;

import java.time.LocalDateTime;

@Data
public class ManagerDetailsResponse {
    private String uniqId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private LocalDateTime created;
    private LocalDateTime blocked;
    private boolean isActive;
    private CreatedByResponse createdBy;
    private BlockedAtResponse blockedAt;

    public ManagerDetailsResponse(Manager manager,
                                  CreatedByResponse createdBy,
                                  BlockedAtResponse blockedAt) {
        this.uniqId = manager.getUniqId();
        this.firstName = manager.getFirstName();
        this.lastName = manager.getLastName();
        this.username = manager.getUser().getUsername();
        this.email = manager.getUser().getEmail();
        this.blocked = manager.getBlocked();
        this.isActive = manager.getBlocked() != null;
        this.createdBy = createdBy;
        this.blockedAt = blockedAt;
    }
}
