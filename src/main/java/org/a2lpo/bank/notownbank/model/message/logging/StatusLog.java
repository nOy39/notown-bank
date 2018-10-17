package org.a2lpo.bank.notownbank.model.message.logging;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * таблица соотношения логов к статусу
 */
@Entity
@Table(name = "log_status_table")
public class StatusLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private Status status;
}
