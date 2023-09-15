package ru.m4nveru.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Author {
    private int id;
    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Имя должно состоять из 3 и более символов (не больше 100)")
    private String name;

    public Author() {
    }

    public Author(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
