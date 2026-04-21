package com.simplon.jpalibrary.controller;

import com.simplon.jpalibrary.model.Book;
import com.simplon.jpalibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Iterable<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {

        return bookService.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {

        return bookService.saveBook(book);
    }

    @PutMapping("{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {

        return bookService.saveBook(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

}
