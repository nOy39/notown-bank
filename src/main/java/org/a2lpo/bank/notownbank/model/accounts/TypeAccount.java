package org.a2lpo.bank.notownbank.model.accounts;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * таблица типов соотношений типо счетов
 */
@Entity
@Table(name = "type")
@Data
public class TypeAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private TypeAccountName type;

    public TypeAccount() {
    }

    public TypeAccount(TypeAccountName name) {
        this.type = name;
    }
}
