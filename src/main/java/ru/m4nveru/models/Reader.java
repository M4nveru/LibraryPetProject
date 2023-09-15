package ru.m4nveru.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Scanner;

public class Reader {
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 3, max = 100, message = "Имя должно состоять из 3 и более символов (не больше 100)")
    private String name;
    @Pattern(regexp = "(19\\d\\d)|(20\\d\\d)", message = "Год рождения должен быть валидным")
    private int birth_year;

    public Reader() {
    }

    public Reader(int id, String name, int birth_year) {
        this.id = id;
        this.name = name;
        this.birth_year = birth_year;
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

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }
}
