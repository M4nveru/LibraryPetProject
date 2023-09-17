package ru.m4nveru.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.m4nveru.dao.AuthorDAO;
import ru.m4nveru.models.Author;

@Component
public class AuthorValidator implements Validator {
    private final AuthorDAO authorDAO;

    @Autowired
    public AuthorValidator(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Author.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Author author = (Author) target;
        if (authorDAO.get(author.getName()).isPresent() && author.getId()!=(authorDAO.get(author.getName()).get().getId())){
            errors.rejectValue("name", "", "Автор с таким именем уже существует!");
        }
    }
}
