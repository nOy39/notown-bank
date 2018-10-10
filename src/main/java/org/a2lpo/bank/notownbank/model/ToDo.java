package org.a2lpo.bank.notownbank.model;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.audit.UserDateAudit;

import javax.persistence.*;

@Entity
@Table(name = "todo")
@Data
public class ToDo extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private boolean isDone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private ToDo parentTask;

    public ToDo() {
    }

    public ToDo(String message,
                boolean isDone,
                ToDo parentTask) {
        this.message = message;
        this.isDone = isDone;
        this.parentTask = parentTask;
    }
}
