package org.example;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "classes")
@Data
public class SpellClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}