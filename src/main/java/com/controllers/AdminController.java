package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.GetModel;
import com.models.entities.AuthorEntity;
import com.models.entities.BookEntity;
import com.models.entities.GenreEntity;
import com.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping
public class AdminController {

    @Autowired
    BooksService booksService;

    @GetMapping("/new_book")
    public String bookForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "admin/create_book";
    }

    @PostMapping("/new_book")
    public String bookSubmit(@ModelAttribute BookEntity book) {
        booksService.saveBook(book);
        return "redirect:/admin_menu";
    }

    @GetMapping("/genre")
    public String genreForm(Model model) {
        model.addAttribute("genre", new GenreEntity());
        return "admin/create_genre";
    }

    @PostMapping("/genre")
    public String genreSubmit(@ModelAttribute GenreEntity genre) {
        booksService.saveGenre(genre);
        return "redirect:/admin_menu";
    }

    @GetMapping("/auth")
    public String authForm(Model model) {
        model.addAttribute("auth", new AuthorEntity());
        return "admin/create_author";
    }

    @PostMapping("/auth")
    public String authSubmit(@ModelAttribute AuthorEntity auth) {
        booksService.saveAuthor(auth);
        return "redirect:/admin_menu";
    }

    @GetMapping("/set_genre/{id}")
    public String setGenreForm(HttpServletResponse httpServletResponse, Model model, @PathVariable("id") int id)
            throws IOException {
        model.addAttribute("book", new GetModel());
        BookEntity book = booksService.getBookById(id);
        this.id = id;


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBook = objectMapper.writeValueAsString(book);
        String jsonAllGenres = objectMapper.writeValueAsString(booksService.getAllGenres());
        String jsonBookGenres = objectMapper.writeValueAsString(book.getGenres());

        httpServletResponse.addCookie(new Cookie("book", URLEncoder.encode(jsonBook, "UTF-8")));

        if (book.getGenres() != null) {
            httpServletResponse.addCookie(new Cookie("genres", URLEncoder.encode(jsonBookGenres, "UTF-8")));
        } else {
            httpServletResponse.addCookie(new Cookie("genres", URLEncoder.encode("{}", "UTF-8")));
        }

        httpServletResponse.addCookie(new Cookie("allGenres", URLEncoder.encode(jsonAllGenres, "UTF-8")));
        return "admin/change_genre";

    }

    @PostMapping("/set_genre")
    public String homeSubmit(@ModelAttribute GetModel getModel) {
        if (getModel.getTypeE().equals("addToBook")) {
            int sendbookId = Integer.parseInt(getModel.getId());

            int sendGenreId = Integer.parseInt(getModel.getSecondId());

            booksService.saveGenreInBook(sendbookId, sendGenreId);

        } else if (getModel.getTypeE().equals("RemoveFromBook")) {
            booksService.deleteGenreInBook(Integer.parseInt(getModel.getId()), Integer.parseInt(getModel.getSecondId()));
        }
        return "redirect:/set_genre/" + getModel.getId();
    }

    @GetMapping("/set_auth/{id}")
    public String setAuthForm(HttpServletResponse httpServletResponse, Model model, @PathVariable("id") int id)
            throws IOException {
        model.addAttribute("book", new GetModel());
        BookEntity book = booksService.getBookById(id);
        this.id = id;


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBook = objectMapper.writeValueAsString(book);
        String jsonAllGenres = objectMapper.writeValueAsString(booksService.getAllAuthors());
        String jsonBookGenres = objectMapper.writeValueAsString(book.getAuthors());

        httpServletResponse.addCookie(new Cookie("book", URLEncoder.encode(jsonBook, "UTF-8")));

        if (book.getGenres() != null) {
            httpServletResponse.addCookie(new Cookie("auth", URLEncoder.encode(jsonBookGenres, "UTF-8")));
        } else {
            httpServletResponse.addCookie(new Cookie("auth", URLEncoder.encode("{}", "UTF-8")));
        }

        httpServletResponse.addCookie(new Cookie("allAuth", URLEncoder.encode(jsonAllGenres, "UTF-8")));
        return "admin/change_auth";

    }

    @PostMapping("/set_auth")
    public String authSubmit(@ModelAttribute GetModel getModel) {
        if (getModel.getTypeE().equals("addToBook")) {
            int sendBookId = Integer.parseInt(getModel.getId());

            int sendAuthId = Integer.parseInt(getModel.getSecondId());

            booksService.saveAuthInBook(sendBookId, sendAuthId);

        } else if (getModel.getTypeE().equals("RemoveFromBook")) {
            booksService.deleteAuthInBook(Integer.parseInt(getModel.getId()), Integer.parseInt(getModel.getSecondId()));
        }
        return "redirect:/set_auth/" + getModel.getId();
    }

    int id;

    @GetMapping("/change_book/{id}")
    public String setBookForm(HttpServletResponse httpServletResponse, Model model, @PathVariable("id") int id)
            throws IOException {
        BookEntity bookById = booksService.getBookById(id);
        if (bookById != null) {
            model.addAttribute("book", new BookEntity());

            this.id = id;
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonBook = objectMapper.writeValueAsString(bookById);
            httpServletResponse.addCookie(new Cookie("book", URLEncoder.encode(jsonBook, "UTF-8")));

            String jsonBookAuth = objectMapper.writeValueAsString(bookById.getAuthors());
            httpServletResponse.addCookie(new Cookie("bookAuth", URLEncoder.encode(jsonBookAuth, "UTF-8")));

            String jsonBookGenres = objectMapper.writeValueAsString(bookById.getGenres());
            httpServletResponse.addCookie(new Cookie("bookGenres", URLEncoder.encode(jsonBookGenres, "UTF-8")));
            this.id = bookById.getId();
            return "admin/change_book";
        }
        return "redirect:/admin_menu";

    }

    @PostMapping("/change_book")
    public String bookChangeSubmit(@ModelAttribute BookEntity book) {
        booksService.updateBook(book.getName(), book.getDescription(), book.getPrice(), id);
        return "redirect:/change_book/" + book.getId();
    }

    @GetMapping("/books_auth")
    public String setBookAuthForm(HttpServletResponse httpServletResponse, Model model)
            throws IOException {

        model.addAttribute("getModel", new GetModel());

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonBooks = objectMapper.writeValueAsString(booksService.findAllBooks());
        if ((jsonBooks = objectMapper.writeValueAsString(booksService.findAllBooks())) != null)
            httpServletResponse.addCookie(new Cookie("allBooks", URLEncoder.encode(jsonBooks, "UTF-8")));
        else
            httpServletResponse.addCookie(new Cookie("allBooks", URLEncoder.encode("{}", "UTF-8")));

        return "admin/all_books_author";

    }

    @PostMapping("/books_auth")
    public String bookAuthChangeSubmit(@ModelAttribute GetModel getModel) {

        return "redirect:/set_auth/" + getModel.getId();
    }

    @GetMapping("/books_genre")
    public String setBookGenreForm(HttpServletResponse httpServletResponse, Model model)
            throws IOException {

        model.addAttribute("getModel", new GetModel());

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonBooks = objectMapper.writeValueAsString(booksService.findAllBooks());
        if ((jsonBooks = objectMapper.writeValueAsString(booksService.findAllBooks())) != null)
            httpServletResponse.addCookie(new Cookie("allBooks", URLEncoder.encode(jsonBooks, "UTF-8")));
        else
            httpServletResponse.addCookie(new Cookie("allBooks", URLEncoder.encode("{}", "UTF-8")));

        return "admin/all_books_genre";

    }

    @PostMapping("/books_genre")
    public String bookAuthGenreSubmit(@ModelAttribute GetModel getModel) {
        return "redirect:/set_genre/" + getModel.getId();
    }

    @GetMapping("/books_all_change")
    public String setBookChangeForm(HttpServletResponse httpServletResponse, Model model)
            throws IOException {
        model.addAttribute("getModel", new GetModel());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBooks = objectMapper.writeValueAsString(booksService.findAllBooks());
        if ((jsonBooks = objectMapper.writeValueAsString(booksService.findAllBooks())) != null)
            httpServletResponse.addCookie(new Cookie("allBooks", URLEncoder.encode(jsonBooks, "UTF-8")));
        else
            httpServletResponse.addCookie(new Cookie("allBooks", URLEncoder.encode("{}", "UTF-8")));

        return "admin/all_books";
    }

    @PostMapping("/books_all_change")
    public String bookChangeSubmit(@ModelAttribute GetModel getModel) {
        return "redirect:/change_book/" + getModel.getId();
    }

    @GetMapping("/admin_menu")
    public String adminMenu() {
        return "admin/admin_menu";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }
}
