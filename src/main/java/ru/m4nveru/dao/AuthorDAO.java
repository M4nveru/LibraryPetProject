package ru.m4nveru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.m4nveru.models.Author;

import java.util.List;

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
