package org.a2lpo.bank.notownbank.model.accounts.eav;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "eav_type")
@Data
public class TypeAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String typeName;
    private String typeCode;

    public TypeAccount() {
    }
}
