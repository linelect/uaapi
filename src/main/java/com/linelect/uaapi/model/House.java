package com.linelect.uaapi.model;

import javax.persistence.*;

@Entity
@Table(name = "house", indexes = {@Index(name = "house_number_idx", columnList = "house_number")})
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "house_number", nullable = false, length = 50)
    private String houseNumber;

    @Column(name = "post_index")
    private int postIndex;

    @ManyToOne
    private Street street;

    public House() {
    }

    public House(String houseNumber, int postIndex, Street street) {
        this.houseNumber = houseNumber;
        this.postIndex = postIndex;
        this.street = street;
    }

    public House(Long id, String houseNumbers, int postIndex, Street street) {
        this(houseNumbers, postIndex, street);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public int getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(int postIndex) {
        this.postIndex = postIndex;
    }
}
