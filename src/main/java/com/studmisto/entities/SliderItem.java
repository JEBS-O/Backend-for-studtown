package com.studmisto.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "slider_items")
public class SliderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "photo_link")
    @NotNull
    private String photoLink;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "description")
    @NotNull
    private String description;
}
