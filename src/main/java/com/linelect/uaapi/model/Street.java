package com.linelect.uaapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "street", indexes = {@Index(name = "street_id_idx", columnList = "id"),
        @Index(name = "street_name_idx", columnList = "name")})
public class Street {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Settlement settlement;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    public Street() {
    }

    public Street(Settlement settlement, String name) {
        this.settlement = settlement;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Settlement getSettlement() {
        return settlement;
    }

    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }
}
