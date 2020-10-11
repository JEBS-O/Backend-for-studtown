package com.studmisto.entities;

import javax.persistence.*;

@Entity
@Table(name = "slider_items")
public class SliderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String photoLink;
    private String title;
    private String description;

    public SliderItem() {

    }
    public SliderItem(String photoLink, String title, String description) {
        this.photoLink = photoLink;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
