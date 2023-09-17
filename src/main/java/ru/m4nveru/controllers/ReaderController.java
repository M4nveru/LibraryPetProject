package ru.m4nveru.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.m4nveru.dao.ReaderDAO;
import ru.m4nveru.models.Reader;
import ru.m4nveru.util.ReaderValidator;

@Controller
@RequestMapping("/readers")
public class ReaderController{
    private final ReaderDAO readerDAO;
    private final ReaderValidator validator;

    @Autowired
    public ReaderController(ReaderDAO readerDAO, ReaderValidator validator) {
        this.readerDAO = readerDAO;
        this.validator = validator;
    }

    @GetMapping()
    public String showReaders(Model model){
        model.addAttribute("readers", readerDAO.getAll());
        return "/readers/showAll";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("reader") Reader reader){
        return "/readers/new";
    }

    @PostMapping()
    public String createReader(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult){
        validator.validate(reader, bindingResult);
        if (bindingResult.hasErrors()){
            return "/readers/new";
        }
        readerDAO.create(reader);
        return "redirect:/readers";
    }

    @GetMapping("/{id}")
    public String showReader(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerDAO.get(id));
        model.addAttribute("books", readerDAO.getReaderBooks(id));
        return "/readers/show";
    }

    @GetMapping("/{id}/edit")
    public String editReader(@PathVariable("id") int id, Model model){
        model.addAttribute("reader", readerDAO.get(id));
        return "/readers/edit";
    }

    @PatchMapping("/{id}")
    public String updateReader(@ModelAttribute("reader") @Valid Reader reader, BindingResult bindingResult, @PathVariable("id") int id){
        validator.validate(reader, bindingResult);
        if (bindingResult.hasErrors()){
            return "/readers/edit";
        }
        readerDAO.update(id, reader);
        return "redirect:/readers";
    }

    @DeleteMapping("/{id}")
    public String deleteReader(@PathVariable("id") int id){
        readerDAO.delete(id);
        return "redirect:/readers";
    }
}
