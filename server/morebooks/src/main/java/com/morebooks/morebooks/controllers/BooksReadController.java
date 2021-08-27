package com.morebooks.morebooks.controllers;

import com.morebooks.morebooks.domain.requests.BooksReadInsertRequest;
import com.morebooks.morebooks.domain.requests.BooksReadRequest;
import com.morebooks.morebooks.entities.Book;
import com.morebooks.morebooks.services.BooksReadService;
import com.morebooks.morebooks.validators.books.BooksReadInsertValidator;
import com.morebooks.morebooks.validators.books.BooksReadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksReadController {

    @Autowired
    private BooksReadService service;

    @Autowired
    private BooksReadValidator booksReadValidator;

    @Autowired
    private BooksReadInsertValidator booksReadInsertValidator;


    @GetMapping("/read-books/user/{userId}")
    public List<Book> getListOfReadBooks(@PathVariable String userId) throws Exception {

        BooksReadRequest request = new BooksReadRequest();
        request.setUserId(userId);

        // validate req
        booksReadValidator.validate(request);
        List<Book> books = service.getListOfReadBooks(userId);

        return books;
    }

    @PostMapping("/read-books/user/{userId}/book/{bookId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void insertBookToReadBooksList(@PathVariable String userId, @PathVariable String bookId) throws Exception {

        BooksReadInsertRequest request = new BooksReadInsertRequest();
        request.setUserId(userId);
        request.setBookId(bookId);

        // validate req
        booksReadInsertValidator.validate(request);

        // insert book
        service.insertBookToReadBooksList(userId, bookId);
    }

}
