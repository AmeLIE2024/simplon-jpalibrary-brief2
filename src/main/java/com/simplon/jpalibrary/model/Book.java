package com.simplon.jpalibrary.model;

import jakarta.persistence.*;
import lombok.Data;

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

    @Column(nullable = false)
    private Boolean available;

}
