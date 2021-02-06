package com.studmisto.entities;

import com.studmisto.entities.enums.Dorm;
import com.studmisto.entities.enums.Gender;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "room_number")
    private Integer roomNumber;
    @Column(name = "stage")
    private Integer stage;
    @Enumerated(EnumType.STRING)
    @Column(name = "dorm")
    private Dorm dorm;
    @Column(name = "places")
    private Integer places;
    @Column(name = "available_places")
    private Integer availablePlaces;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
}
