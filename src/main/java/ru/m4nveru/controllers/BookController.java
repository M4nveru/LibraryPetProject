package ru.m4nveru.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.m4nveru.dao.AuthorDAO;
import ru.m4nveru.dao.BookDAO;
import ru.m4nveru.dao.ReaderDAO;
import ru.m4nveru.models.Author;
import ru.m4nveru.models.Book;
import ru.m4nveru.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final ReaderDAO readerDAO;
    private final BookValidator validator;

    @Autowired
    public BookController(BookDAO bookDAO, AuthorDAO authorDAO, ReaderDAO readerDAO, BookValidator validator) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.readerDAO = readerDAO;
        this.validator = validator;
    }

    @GetMapping()
    public String showBooks(Model model){
        model.addAttribute("books", bookDAO.getAll());
        return "/books/showAll";
    }

    @GetMapping("/new")
    public String createForm(Model model, @ModelAttribute("book") Book book){
        model.addAttribute("authors", authorDAO.getAll());
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, Model model){
        validator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("authors", authorDAO.getAll());
            return "/books/new";
        }
        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model){
        if (bookDAO.get(id)!=null){
            model.addAttribute("book", bookDAO.get(id));
            model.addAttribute("readers", readerDAO.getAll());
            return "/books/show";
        }
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") int id, Model model){
        if (bookDAO.get(id)!=null){
            model.addAttribute("book", bookDAO.get(id));
            model.addAttribute("authors", authorDAO.getAll());
            return "/books/edit";
        }
        return "redirect:/books";
    }

    @PatchMapping("/{id}/edit")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        validator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("authors", bookDAO.getAll());
            return "/books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String appointReader(@PathVariable("id") int id, @ModelAttribute("book") Book book){
        if (book.getReader() != null){
            bookDAO.appointReader(id, book.getReader().getId());
        }
        else {
            bookDAO.appointReader(id, null);
        }
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
