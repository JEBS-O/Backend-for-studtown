package com.studmisto.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
public class ContactItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private ContactCategory category;
    private String name;
    private String position;
    private String address;
    private String email;
    private String phoneNumber1;
    private String phoneNumber2;

    public ContactItem() {
    }

    public ContactItem(ContactCategory category, String name, String position, String address, @Email String email, String phoneNumber1, String phoneNumber2) {
        this.category = category;
        this.name = name;
        this.position = position;
        this.address = address;
        this.email = email;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public ContactCategory getCategory() {
        return category;
    }

    public void setCategory(ContactCategory contactCategory) {
        this.category = contactCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }
}
