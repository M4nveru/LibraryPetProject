package ru.m4nveru.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.m4nveru.dao.AuthorDAO;
import ru.m4nveru.dao.ReaderDAO;
import ru.m4nveru.models.Author;
import ru.m4nveru.models.Book;
import ru.m4nveru.models.Reader;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookRowMapper implements RowMapper<Book> {
    private final AuthorDAO authorDAO;
    private final ReaderDAO readerDAO;

    @Autowired
    public BookRowMapper(AuthorDAO authorDAO, ReaderDAO readerDAO) {
        this.authorDAO = authorDAO;
        this.readerDAO = readerDAO;
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        if (rs.getInt("reader_id") == 0) {
            int author_id = rs.getInt("author_id");
            return new Book(rs.getInt("id"),
                    rs.getString("title"),
                    new Author(author_id, authorDAO.get(author_id).getName()),
                    rs.getInt("year"),
                    null);
        }
        else {
            int reader_id = rs.getInt("reader_id");
            int author_id = rs.getInt("author_id");
            return new Book(rs.getInt("id"),
                    rs.getString("title"),
                    new Author(author_id, authorDAO.get(author_id).getName()),
                    rs.getInt("year"),
                    new Reader(reader_id, readerDAO.get(reader_id).getName(), readerDAO.get(reader_id).getBirth_year())
            );
        }
    }
}
