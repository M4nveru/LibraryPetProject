package ru.m4nveru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.m4nveru.models.Book;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll(){
        return jdbcTemplate.query("SELECT b.id, b.title, a.id, a.name, b.year, r.id, r.name, r.birth_year " +
                "FROM books b JOIN authors a ON b.author_id=a.id" +
                " LEFT JOIN readers r ON b.reader_id = r.id", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book get(int id){
        return jdbcTemplate.query("SELECT b.id, b.title, a.id, a.name, b.year, r.id, r.name, r.birth_year " +
                "FROM books b JOIN authors a ON b.author_id=a.id" +
                " LEFT JOIN readers r ON b.reader_id = r.id WHERE b.id=?",
                new BeanPropertyRowMapper<>(Book.class), id)
                .stream().findAny().orElse(null);
    }

    public void create(Book book){
        jdbcTemplate.update("INSERT INTO books (title, author_id, year) " +
                "VALUES (?,?,?)", book.getTitle(), book.getAuthor().getId(), book.getYear());
    }

    public void update(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE books SET title=?, author_id=?, year=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor().getId(), updatedBook.getYear(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }
}
