package ru.m4nveru.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.m4nveru.dao.BookDAO;
import ru.m4nveru.models.Book;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDAO.get(book.getTitle(), book.getAuthor(), book.getYear()).isPresent() && book.getId()!=bookDAO.get(book.getTitle(), book.getAuthor(), book.getYear()).get().getId()){
            errors.rejectValue("year", "", "Данная книга у автора уже существует!");
        }
    }
}
