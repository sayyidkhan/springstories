package com.main.springstories.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "AUTHOR")
public class AuthorEntity {

    @Id
    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="BIRTHDAY", nullable = false, length = 10)
    // format: dd/mm/yyyy
    private String birthday;

    // Constructors
    public AuthorEntity() {
    }

    public AuthorEntity(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    // toString method for easier debugging and logging
    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

}

