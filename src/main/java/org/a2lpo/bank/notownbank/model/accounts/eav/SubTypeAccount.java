package org.a2lpo.bank.notownbank.model.accounts.eav;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "eav_subtype")
@Data
public class SubTypeAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subTypeName;
    private String subTypeCode;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private TypeAccount type;

    public SubTypeAccount() {
    }
}
