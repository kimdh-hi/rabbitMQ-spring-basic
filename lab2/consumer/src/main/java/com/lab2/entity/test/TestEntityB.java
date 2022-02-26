package com.lab2.entity.test;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class TestEntityB {

    private Long id;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public TestEntityB() {
    }

    public TestEntityB(Long id, String name, String description, LocalDate date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TestEntityB{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
