package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Manager;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class ManagerDetailsResponse {
    private String uniqId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime blockedAt;
    private boolean isActive;
    private UserAuditResponse createdBy;
    private UserAuditResponse updateBy;

    public ManagerDetailsResponse(Manager manager,
                                  UserAuditResponse createdBy,
                                  UserAuditResponse updateBy) {
        this.uniqId = manager.getUniqId();
        this.firstName = manager.getFirstName();
        this.lastName = manager.getLastName();
        this.username = manager.getUser().getUsername();
        this.email = manager.getUser().getEmail();
        this.createdAt = manager.getCreatedAt();
        this.updatedAt = manager.getUpdatedAt();
        this.blockedAt = manager.getBlockedAt();
        this.isActive = manager.isActive();
        this.createdBy = createdBy;
        this.updateBy = updateBy;
    }
}
