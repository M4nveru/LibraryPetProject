package ru.m4nveru.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.m4nveru.dao.AuthorDAO;
import ru.m4nveru.models.Author;
import ru.m4nveru.util.AuthorValidator;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorDAO authorDAO;
    private final AuthorValidator validator;

    @Autowired
    public AuthorController(AuthorDAO authorDAO, AuthorValidator validator) {
        this.authorDAO = authorDAO;
        this.validator = validator;
    }

    @GetMapping()
    public String showAuthors(Model model){
        model.addAttribute("authors", authorDAO.getAll());
        return "/authors/showAll";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("author") Author author){
        return "/authors/new";
    }

    @PostMapping()
    public String createAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult){
        validator.validate(author, bindingResult);
        if (bindingResult.hasErrors()){
            return "/authors/new";
        }
        authorDAO.create(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String showAuthor(@PathVariable("id") int id, Model model){
        if (authorDAO.get(id)!=null){
            model.addAttribute("author", authorDAO.get(id));
            model.addAttribute("books", authorDAO.getAuthorBooks(id));
            return "/authors/show";
        }
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String editAuthor(@PathVariable("id") int id, Model model){
        if (authorDAO.get(id)!=null){
            model.addAttribute("author", authorDAO.get(id));
            return "/authors/edit";
        }
        return "redirect:/authors";
    }

    @PatchMapping("/{id}")
    public String updateAuthor(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult, @PathVariable("id") int id){
        validator.validate(author, bindingResult);
        if (bindingResult.hasErrors()){
            return "/authors/edit";
        }
        authorDAO.update(id, author);
        return "redirect:/authors";
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable("id") int id, Model model){
        try {
            authorDAO.delete(id);
            return "redirect:/authors";
        }
        catch (DataIntegrityViolationException e){
            model.addAttribute("author", authorDAO.get(id));
            model.addAttribute("books", authorDAO.getAuthorBooks(id));
            model.addAttribute("errors", "Ошибка удаления: у автора имеются книги");
            return "/authors/show";
        }
    }
}
