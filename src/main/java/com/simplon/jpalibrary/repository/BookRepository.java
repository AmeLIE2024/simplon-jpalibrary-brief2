package com.simplon.jpalibrary.repository;

import com.simplon.jpalibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // trouver un livre par son titre.
    Optional<Book> findByTitle(String title);

}
