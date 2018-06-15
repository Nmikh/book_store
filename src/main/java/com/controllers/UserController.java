package com.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.models.GetModel;
import com.models.entities.AuthorEntity;
import com.models.entities.BookEntity;
import com.models.entities.GenreEntity;
import com.models.entities.OrderEntity;
import com.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    BooksService booksService;

    @GetMapping("/books")
    public String bookForm(HttpServletResponse httpServletResponse, Model model) throws JsonProcessingException, UnsupportedEncodingException {
        model.addAttribute("getModel", new GetModel());
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonAllBooksByAuthors = objectMapper.writeValueAsString(booksService.getAllBooksSortByAuthor());
        httpServletResponse.addCookie(new Cookie("allBooksByAuthors", URLEncoder.encode(jsonAllBooksByAuthors, "UTF-8")));

        String jsonAllBooksByGenres = objectMapper.writeValueAsString(booksService.getAllBooksSortByGenres());
        httpServletResponse.addCookie(new Cookie("allBooksByGenres", URLEncoder.encode(jsonAllBooksByGenres, "UTF-8")));
        return "client/order";
    }

    @PostMapping("/books")
    public String bookSubmit(@ModelAttribute GetModel getModel) {
        return "redirect:/buy/" + getModel.getId();
    }

    private int id;

    @GetMapping("/buy/{id}")
    public String orderForm(HttpServletResponse httpServletResponse, @PathVariable("id") int id, Model model)
            throws JsonProcessingException, UnsupportedEncodingException {
        BookEntity bookById = booksService.getBookById(id);
        if (bookById != null) {
            model.addAttribute("order", new OrderEntity());

            ObjectMapper objectMapper = new ObjectMapper();

            String jsonBook = objectMapper.writeValueAsString(bookById);
            httpServletResponse.addCookie(new Cookie("book", URLEncoder.encode(jsonBook, "UTF-8")));

            String jsonBookAuth = objectMapper.writeValueAsString(bookById.getAuthors());
            httpServletResponse.addCookie(new Cookie("bookAuth", URLEncoder.encode(jsonBookAuth, "UTF-8")));

            String jsonBookGenres = objectMapper.writeValueAsString(bookById.getGenres());
            httpServletResponse.addCookie(new Cookie("bookGenres", URLEncoder.encode(jsonBookGenres, "UTF-8")));
            this.id = bookById.getId();
            return "client/buy";
        }
        return "redirect:/books";
    }

    @PostMapping("/buy")
    public String orderSubmit(@ModelAttribute OrderEntity order) {
        order.setBook(booksService.getBookById(id));
        booksService.saveOrder(order);
        return "redirect:/books";
    }

}
