package com.studmisto.entities;

import jdk.jfr.DataAmount;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usr")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String userpic;

    public User() {

    }

    public User(String id, String name, String email, String userpic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userpic = userpic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }
}
