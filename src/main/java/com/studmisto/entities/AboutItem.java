package com.studmisto.entities;

import javax.persistence.*;

@Entity
@Table(name = "about_items")
public class AboutItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String iconLink;

    public AboutItem() {

    }
    public AboutItem(String title, String description, String iconLink) {
        this.title = title;
        this.description = description;
        this.iconLink = iconLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }
}
