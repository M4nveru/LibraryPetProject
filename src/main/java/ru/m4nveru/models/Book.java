package ru.m4nveru.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 1, message = "Название книги должно состоять из 1 и более символов")
    private String title;
    @NotEmpty(message = "Необходимо выбрать автора книги")
    private Author author;
    @Pattern(regexp = "(\\d{3})|(\\d{4})", message = "Год книги должен быть валидным")
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
