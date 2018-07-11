package com.linelect.uaapi.model;

import javax.persistence.*;

@Entity
@Table(name = "region", indexes = {@Index(name = "region_id_idx", columnList = "id"),
        @Index(name = "region_name_idx", columnList = "name")})
public class Region {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    public Region() {
    }

    public Region(String name) {
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
}
