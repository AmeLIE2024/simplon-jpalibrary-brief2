package com.simplon.jpalibrary.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

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

}
