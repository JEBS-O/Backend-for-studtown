package com.studmisto.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "about_items")
public class AboutItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "description")
    @NotNull
    private String description;
    @Column(name = "iconLink")
    @NotNull
    private String iconLink;
}
