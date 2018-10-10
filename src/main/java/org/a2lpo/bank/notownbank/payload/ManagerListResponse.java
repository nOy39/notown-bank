package org.a2lpo.bank.notownbank.payload;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.Manager;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class ManagerListResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String uniqId;
    private String personalPage;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime blockedAt;
    public ManagerListResponse(Manager manager) {
        this.id = manager.getId();
        this.firstName = manager.getFirstName();
        this.lastName = manager.getLastName();
        this.uniqId = manager.getUniqId();
        this.personalPage = manager.getPersonalPage();
        this.isActive = manager.isActive();
        this.createdAt = manager.getCreatedAt();
        this.blockedAt = manager.getBlockedAt();
    }
}
