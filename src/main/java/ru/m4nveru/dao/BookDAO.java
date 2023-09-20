package ru.m4nveru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.m4nveru.models.Author;
import ru.m4nveru.models.Book;
import ru.m4nveru.util.BookRowMapper;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final AuthorDAO authorDAO;
    private final ReaderDAO readerDAO;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, AuthorDAO authorDAO, ReaderDAO readerDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorDAO = authorDAO;
        this.readerDAO = readerDAO;
    }

    public List<Book> getAll(){
        return jdbcTemplate.query("SELECT * FROM books",
                new BookRowMapper(authorDAO, readerDAO));
    }

    public Book get(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE id=?",
                 new BookRowMapper(authorDAO, readerDAO), id)
                .stream().findAny().orElse(null);
    }

    public Optional <Book> get(String title, Author author, int year){
        return jdbcTemplate.query("SELECT * FROM books WHERE title=? AND author_id=? AND year=?",
                        new BookRowMapper(authorDAO, readerDAO), title, author.getId(), year)
                .stream().findAny();
    }

    public List<Book> getRecentBooks(){
        return jdbcTemplate.query("SELECT * FROM books ORDER BY id DESC LIMIT 5", new BookRowMapper(authorDAO, readerDAO));
    }

    public List<Book> getReaderBooks(Integer readerId){
        return jdbcTemplate.query("SELECT * FROM books WHERE reader_id=?", new BookRowMapper(authorDAO, readerDAO), readerId);
    }
    public void create(Book book){
        jdbcTemplate.update("INSERT INTO books (title, author_id, year) " +
                "VALUES (?,?,?)", book.getTitle(), book.getAuthor().getId(), book.getYear());
    }

    public void update(int id, Book updatedBook){
        jdbcTemplate.update("UPDATE books SET title=?, author_id=?, year=? WHERE id=?",
                updatedBook.getTitle(), updatedBook.getAuthor().getId(), updatedBook.getYear(), id);
    }

    public void appointReader(int book_id, Integer reader_id){
        jdbcTemplate.update("UPDATE books SET reader_id=? WHERE id=?", reader_id, book_id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }
}
