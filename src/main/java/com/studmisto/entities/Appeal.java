package com.studmisto.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.studmisto.entities.enums.AppealCategory;
import com.studmisto.entities.enums.AppealStatus;
import com.studmisto.entities.enums.Status;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "appeals")
@Data
public class Appeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "appeal_category")
    @NotNull
    @Enumerated(EnumType.STRING)
    private AppealCategory appealCategory;
    @Column(name = "appeal_status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AppealStatus appealStatus;
    @Column(name = "recipient")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status recipient;
    @Column(name = "creation_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="HH:mm dd.MM.yyyy")
    @NotNull
    private Date creationDate;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "messages_communication",
            joinColumns = @JoinColumn(name="appeal_id"),
            inverseJoinColumns = @JoinColumn(name="message_id")
    )
    private List<Message> messages;

    public Appeal() {
        creationDate = new Date();
    }
}
