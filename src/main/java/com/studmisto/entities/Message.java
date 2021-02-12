package com.studmisto.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "text")
    private String text;
    @Column(name = "creation_date")
    private Date creationDate;

    public Message() {
        this.creationDate = new Date();
    }
}
