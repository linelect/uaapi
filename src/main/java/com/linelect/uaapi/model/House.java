package com.linelect.uaapi.model;

import javax.persistence.*;

@Entity
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "house_numbers", nullable = false, length = 2000)
    private String houseNumbers;

    @Column(name = "post_index")
    private int postIndex;

    @ManyToOne
    private Street street;

    public House() {
    }

    public House(String houseNumbers, int postIndex, Street street) {
        this.houseNumbers = houseNumbers;
        this.postIndex = postIndex;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseNumbers() {
        return houseNumbers;
    }

    public void setHouseNumbers(String houseNumbers) {
        this.houseNumbers = houseNumbers;
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
