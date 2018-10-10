package org.a2lpo.bank.notownbank.model.message;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class AdminMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Length(max = 2048)
    private String message;
    private String serviceComment;
    private LocalDateTime created;
    private LocalDateTime close;
    private boolean isActive;

    public AdminMessage(String message,
                        String serviceComment) {
        this.message = message;
        this.serviceComment = serviceComment;
        this.created = LocalDateTime.now();
        this.isActive = true;
    }
}
