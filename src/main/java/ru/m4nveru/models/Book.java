package ru.m4nveru.models;

import jakarta.validation.constraints.*;

public class Book {
    private int id;
    @NotEmpty()
    @Size(min = 1, message = "Название книги должно состоять из 1 и более символов")
    private String title;
    private Author author;
    @Min(value = 1, message = "Год должен быть валидным")
    @Max(value = 2023, message = "Год должен быть валидным")
    private int year;
    private Reader reader;

    public Book() {
    }

    public Book(int id, String title, Author author, int year, Reader reader) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.reader = reader;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
