package org.a2lpo.bank.notownbank.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ClientRequest {

    @NotNull
    @Size(min = 3, max = 15)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;
    @NotNull
    private Long phone;
    @NotNull
    private LocalDate dateOfBirth;

    public ClientRequest(@NotBlank String firstName,
                         @NotBlank String lastName,
                         @NotBlank Long phone,
                         @NotBlank LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }
}
