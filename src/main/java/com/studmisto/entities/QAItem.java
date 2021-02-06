package com.studmisto.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name="qa_items")
public class QAItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "question")
    @NotNull
    private String question;
    @Column(name = "answer")
    @NotNull
    private String answer;
}
