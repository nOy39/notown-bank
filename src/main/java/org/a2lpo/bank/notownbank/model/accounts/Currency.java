package org.a2lpo.bank.notownbank.model.accounts;

import lombok.Data;
import org.a2lpo.bank.notownbank.model.audit.RoleName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Промежуточная таблица Валют
 */
@Entity
@Table(name = "currency")
@Data
public class Currency implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private CurrencyName name;

    public Currency() {
    }

    public Currency(CurrencyName name) {
        this.name = name;
    }
}
