package org.a2lpo.bank.notownbank.model.accounts.eav;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "eav_currency",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "currencyName"),
                @UniqueConstraint(columnNames = "currencyCode")
        })
@Data
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String currencyName;
    private String currencyCode;

    public Currency() {
    }

    public Currency(String currencyName, String currencyCode) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
    }
}
