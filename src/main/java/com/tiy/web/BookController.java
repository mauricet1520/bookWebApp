package com.tiy.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crci1 on 1/10/2017.
 */

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepo;

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(HttpSession session, String userName, String password) throws Exception {
        User user = users.findFirstByName(userName);
        if (user == null) {
            user = new User(userName, password);
            users.save(user);
        }
        else if (!password.equals(user.getPassword())) {
            throw new Exception("Incorrect password");
        }
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session, String genre, String title, String author) {
        User savedUser = (User)session.getAttribute("user");


        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }
        List<Book> bookList = new ArrayList<>();
        if (genre != null) {
            if (savedUser != null){
                bookList = bookRepo.findByUserAndGenre(savedUser, genre);
            }else {
                bookList = bookRepo.findByGenre(genre);
            }
        }else if (title != null) {
            bookList = bookRepo.findByTitle(title);
        }else if (author != null) {
            bookList = bookRepo.findByAuthor(author);
        }else {
            if (savedUser != null) {
                bookList = bookRepo.findByUser(savedUser);
            }else {
            Iterable<Book> allBooks = bookRepo.findAll();
            for (Book myBook : allBooks) {
                bookList.add(myBook);
            }}
        }

        model.addAttribute("books", bookList);
        return "home";

    }

    @RequestMapping(path = "/add-book", method = RequestMethod.POST)
    public String addBook(HttpSession session, String bookTitle, String bookAuthor, String bookGenre){
        User user = (User) session.getAttribute("user");

        Book book = new Book(bookTitle, bookAuthor, bookGenre, false, user);
        bookRepo.save(book);
        return "redirect:/";
    }

    @RequestMapping(path = "/searchByTitle", method = RequestMethod.GET)
    public String queryTitle(Model model, String searchTitle, HttpSession session){
        User user = (User) session.getAttribute("user");

        List<Book> bookList = bookRepo.findByTitleStartsWith(searchTitle, user.getId());
        model.addAttribute("books", bookList);
        model.addAttribute("user", user);

        return "home";
    }

    @RequestMapping(path = "/searchByAuthor", method = RequestMethod.GET)
    public String queryAuthor(Model model, String searchAuthor, HttpSession session){
        User user = (User) session.getAttribute("user");
        List<Book> bookList = bookRepo.findByAuthorStartsWith(searchAuthor, user.getId());
        model.addAttribute("books", bookList);
        model.addAttribute("user", user);

        return "home";
    }

    @RequestMapping(path = "/delete", method = RequestMethod.GET)
    public String deleteBook(Model model, Integer bookID) {
        if (bookID != null) {
            bookRepo.delete(bookID);
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/check_out", method = RequestMethod.GET)
    public String checkOutBook(Model model, Integer bookID) {
        if (bookID != null) {
            Book book = bookRepo.findOne(bookID);
            if (!book.checkedOut) {
                book.checkedOut = true;
            }
            bookRepo.save(book);
        }
        return "redirect:/";

    }

    @RequestMapping(path = "/check_in", method = RequestMethod.GET)
    public String checkInBook(Model model, Integer bookID) {
        if (bookID != null) {
            Book book = bookRepo.findOne(bookID);
            if (book.checkedOut) {
                book.checkedOut = false;
            }
            bookRepo.save(book);
        }
        return "redirect:/";

    }


}
