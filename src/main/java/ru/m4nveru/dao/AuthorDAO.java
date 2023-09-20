package ru.m4nveru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.m4nveru.models.Author;
import ru.m4nveru.models.Book;
import ru.m4nveru.models.Reader;
import ru.m4nveru.util.BookRowMapper;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAO{
    private final JdbcTemplate jdbcTemplate;
    private final ReaderDAO readerDAO;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate, ReaderDAO readerDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.readerDAO = readerDAO;
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
        return jdbcTemplate.query("SELECT * FROM books WHERE author_id=?",
                new BookRowMapper(this, readerDAO), authorId);
    }

    public List<Author> getRecentAuthors(){
        return jdbcTemplate.query("SELECT * FROM authors ORDER BY id DESC LIMIT 5", new BeanPropertyRowMapper<>(Author.class));
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
