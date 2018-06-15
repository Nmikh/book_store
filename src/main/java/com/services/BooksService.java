package com.services;

import com.DAO.repositories.AuthorRepository;
import com.DAO.repositories.BookRepository;
import com.DAO.repositories.GenreRepository;
import com.DAO.repositories.OrderRepository;
import com.models.entities.AuthorEntity;
import com.models.entities.BookEntity;
import com.models.entities.GenreEntity;
import com.models.entities.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookEntity getBookById(int id) {
        try {
            List<BookEntity> byId = bookRepository.findById(id);
            return byId.get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<BookEntity> getAllBooksSortByAuthor() {
        List<AuthorEntity> allAuthors = authorRepository.findAll();
        List<BookEntity> result = new ArrayList<BookEntity>();
        for (int i = 0; i < allAuthors.size(); i++) {
            List<BookEntity> intermediate = new ArrayList<BookEntity>();
            intermediate.addAll(result);

            BookEntity authorCard = new BookEntity();
            authorCard.setPrice(-1.00);
            authorCard.setName(allAuthors.get(i).getName());
            authorCard.setDescription(allAuthors.get(i).getSurname());
            intermediate.add(authorCard);

            List<BookEntity> byAuthors = bookRepository.findByAuthors(allAuthors.get(i));
            intermediate.addAll(byAuthors);
            result = intermediate;
        }

        return result;
    }

    public List<BookEntity> getAllBooksSortByGenres() {
        List<GenreEntity> allGenres = genreRepository.findAll();
        List<BookEntity> result = new ArrayList<BookEntity>();
        for (int i = 0; i < allGenres.size(); i++) {
            List<BookEntity> intermediate = new ArrayList<BookEntity>();
            intermediate.addAll(result);

            BookEntity genreCard = new BookEntity();
            genreCard.setPrice(-1.00);
            genreCard.setName(allGenres.get(i).getName());
            intermediate.add(genreCard);

            List<BookEntity> byGenres = bookRepository.findByGenres(allGenres.get(i));
            intermediate.addAll(byGenres);
            result = intermediate;
        }
        return result;
    }

    public void saveOrder(OrderEntity order) {
        orderRepository.save(order);
    }

    public void saveGenre(GenreEntity genre) {
        genreRepository.save(genre);
    }

    public void saveAuthor(AuthorEntity author) {
        authorRepository.save(author);
    }

    public void saveBook(BookEntity book) {
        bookRepository.save(book);
    }

    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    public void deleteGenreInBook(int id_book, int id_genre) {
        genreRepository.deleteGenreInBook(id_book, id_genre);
    }

    public void saveGenreInBook(int id_book, int id_genre) {
        genreRepository.saveGenreInBook(id_book, id_genre);
    }

    public void deleteAuthInBook(int id_book, int id_genre) {
        authorRepository.deleteAuthInBook(id_book, id_genre);
    }

    public void saveAuthInBook(int id_book, int id_genre) {
        authorRepository.saveAuthInBook(id_book, id_genre);
    }

    public void updateBook(String name, String desc, double price, int id_book) {
        bookRepository.updateBook(name, desc, price, id_book);
    }

    public List<BookEntity> findAllBooks(){
        return bookRepository.findAll();
    }
}
