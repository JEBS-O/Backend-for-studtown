package com.studmisto.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "news_items")
public class NewsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "description")
    @NotNull
    private String description;
    @Column(name = "photo_link")
    @NotNull
    private String photoLink;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="HH:mm dd.MM.yyyy")
    @Column(name = "creation_date")
    @NotNull
    private Date creationDate;

    public NewsItem() {
        this.creationDate = new Date();
    }
}
