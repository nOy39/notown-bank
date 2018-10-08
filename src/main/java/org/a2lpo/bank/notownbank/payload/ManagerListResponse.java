package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import java.net.URI;

@Data
public class ManagerListResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String uniqId;
    private URI personalPage;
    private boolean isActive;

    public ManagerListResponse(Long id,
                               String firstName,
                               String lastName,
                               String uniqId,
                               URI personalPage,
                               boolean isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniqId = uniqId;
        this.personalPage = personalPage;
        this.isActive = isActive;
    }
}
