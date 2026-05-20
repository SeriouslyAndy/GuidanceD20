package org.example;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sources")
@Data
public class SpellSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String abbreviation;
}