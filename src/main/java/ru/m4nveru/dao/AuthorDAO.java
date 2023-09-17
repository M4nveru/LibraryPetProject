package ru.m4nveru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.m4nveru.models.Author;
import ru.m4nveru.models.Book;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAO{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Author> getAll(){
        return jdbcTemplate.query("SELECT * FROM authors", new BeanPropertyRowMapper<>(Author.class));
    }

    public Author get(int id){
        return jdbcTemplate.query("SELECT * FROM authors WHERE id=?", new BeanPropertyRowMapper<>(Author.class), id)
                .stream().findAny().orElse(null);
    }

    public Optional<Author> get(String name){
        return jdbcTemplate.query("SELECT * FROM authors WHERE name=?", new BeanPropertyRowMapper<>(Author.class), name)
                .stream().findAny();
    }

    public List<Book> getAuthorBooks(int authorId){
        return jdbcTemplate.query("SELECT b.id, b.title, a.id, a.name, b.year, r.id, r.name, r.birth_year " +
                        "FROM books b JOIN authors a ON b.author_id=?" +
                        " LEFT JOIN readers r ON b.reader_id = r.id",
                new BeanPropertyRowMapper<>(Book.class), authorId);
    }

    public void create(Author author){
        jdbcTemplate.update("INSERT INTO authors (name) VALUES (?)", author.getName());
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM authors WHERE id=?", id);
    }

    public void update(int id, Author updatedAuthor){
        jdbcTemplate.update("UPDATE authors SET name=? WHERE id=?", updatedAuthor.getName(), id);
    }
}
