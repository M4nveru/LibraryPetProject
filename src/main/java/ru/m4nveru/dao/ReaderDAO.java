package ru.m4nveru.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.m4nveru.models.Book;
import ru.m4nveru.models.Reader;

import java.util.List;

@Component
public class ReaderDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reader> getAll(){
        return jdbcTemplate.query("SELECT * FROM readers", new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader get(int id){
        return jdbcTemplate.query("SELECT * FROM readers WHERE id=?", new BeanPropertyRowMapper<>(Reader.class), id)
                .stream().findAny().orElse(null);
    }

    public List<Book> getReaderBooks(int readerId){
        return jdbcTemplate.query("SELECT b.id, b.title, a.id, a.name, b.year, r.id, r.name, r.birth_year " +
                "FROM books b JOIN authors a ON b.author_id=a.id" +
                " JOIN readers r ON b.reader_id = ?", new BeanPropertyRowMapper<>(Book.class), readerId);
    }

    public void create(Reader reader){
        jdbcTemplate.update("INSERT INTO readers(name, birth_year) VALUES (?,?)",
                reader.getName(), reader.getBirth_year());
    }

    public void update(int id, Reader updatedReader){
        jdbcTemplate.update("UPDATE readers SET name=?, birth_year=? WHERE id=?",
                updatedReader.getName(), updatedReader.getBirth_year(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM readers WHERE id=?", id);
    }
}
