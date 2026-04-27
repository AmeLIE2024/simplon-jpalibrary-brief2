package com.simplon.jpalibrary.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE") //créer une valeur par défaut côté BDD
    private Boolean available = true; //créer une valeur par défaut Java (côté entité)

    @OneToOne
    private Category category;

    @OneToMany
    private List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public Book(Long id, String title, String description, Boolean available, Category category, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.available = available;
        this.category = category;
        this.authors = authors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }
}
