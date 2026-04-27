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

    @GetMapping("/title/{title}")
    public Optional<Book> getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
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
        Optional<Book> b = bookService.getBookById(id);
        if(b.isPresent()) {
            Book currentBook = b.get();

            currentBook.setTitle(book.getTitle());
            currentBook.setDescription(book.getDescription());
            currentBook.setAvailable(book.getAvailable());

            bookService.saveBook(currentBook);
            return currentBook;
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

}
