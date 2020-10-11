package com.studmisto.entities;

import javax.persistence.*;

@Entity
@Table(name="qa_items")
public class QAItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String question;
    private String answer;

    public QAItem() {

    }

    public QAItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
