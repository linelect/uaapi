package com.linelect.uaapi.model;

import javax.persistence.*;

@Entity
@Table(name = "settlement", indexes = {@Index(name = "settlement_id_idx", columnList = "id"),
        @Index(name = "settlement_name_idx", columnList = "name")})
public class Settlement {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne
    private Area area;

    public Settlement() {
    }

    public Settlement(String name, Area area) {
        this.name = name;
        this.area = area;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
