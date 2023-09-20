package ru.m4nveru.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.m4nveru.dao.AuthorDAO;
import ru.m4nveru.dao.BookDAO;
import ru.m4nveru.dao.ReaderDAO;

@RequestMapping("/")
@Controller
public class HomeController {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final ReaderDAO readerDAO;


    @Autowired
    public HomeController(BookDAO bookDAO, AuthorDAO authorDAO, ReaderDAO readerDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.readerDAO = readerDAO;
    }

    @GetMapping()
    public String homePage(Model model){
        model.addAttribute("recentBooks", bookDAO.getRecentBooks());
        model.addAttribute("recentAuthors", authorDAO.getRecentAuthors());
        model.addAttribute("recentReaders", readerDAO.getRecentReaders());
        model.addAttribute("authors", authorDAO.getAll());
        return "/home/homepage";
    }
}
