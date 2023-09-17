package ru.m4nveru.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.m4nveru.dao.ReaderDAO;
import ru.m4nveru.models.Reader;

@Component
public class ReaderValidator implements Validator {
    private final ReaderDAO readerDAO;

    @Autowired
    public ReaderValidator(ReaderDAO readerDAO) {
        this.readerDAO = readerDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Reader.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Reader reader = (Reader) target;
        if (readerDAO.get(reader.getName(), reader.getBirth_year()).isPresent() && reader.getId()!=(readerDAO.get(reader.getName(), reader.getBirth_year()).get().getId())){
            errors.rejectValue("name", "", "Читатель с таким именем и годом рождения уже существует!");
        }
    }
}
