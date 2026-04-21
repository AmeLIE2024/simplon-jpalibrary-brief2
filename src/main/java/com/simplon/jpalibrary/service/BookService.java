package com.simplon.jpalibrary.service;

import com.simplon.jpalibrary.model.Book;
import com.simplon.jpalibrary.repository.BookRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class BookService  {

    @Autowired
    private BookRepository bookRepository;


    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Iterable<Book> getBooks() {
        return bookRepository.findAll();
    }

    public void deleteBookById(Long id){

        bookRepository.deleteById(id);
    }
    public Book saveBook(Book book) {
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

}
