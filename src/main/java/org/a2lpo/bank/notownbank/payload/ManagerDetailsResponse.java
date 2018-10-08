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

    public ManagerDetailsResponse(Manager manager,
                                  CreatedByResponse createdBy) {
        this.uniqId = manager.getUniqId();
        this.firstName = manager.getFirstName();
        this.lastName = manager.getLastName();
        this.username = manager.getUser().getUsername();
        this.email = manager.getUser().getEmail();
        this.created = manager.getCreated();
        this.blocked = manager.getBlocked();
        this.isActive = manager.getBlocked() == null ? true : false;
        this.createdBy = createdBy;
    }
}
