package com.studmisto.entities;

import com.studmisto.entities.enums.ContactCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class ContactItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    @NotNull
    private ContactCategory category;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "position")
    @NotNull
    private String position;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    @NotNull
    @Email
    private String email;
    @Column(name = "phone_number_1")
    @NotNull
    private String phoneNumber1;
    @Column(name = "phone_number_2")
    private String phoneNumber2;
}
