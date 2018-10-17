package org.a2lpo.bank.notownbank.model.message.logging;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Таблица логирования
 */
@Entity
@Table(name = "log")
@Data
public class OperationLog extends UserDateAudit {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Length(max = 4096)
    private String message;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    private StatusLog statusLog;

    public OperationLog(@NotNull String message,
                        @NotNull StatusLog statusLog) {
        this.message = message;
        this.statusLog = statusLog;
    }
}
