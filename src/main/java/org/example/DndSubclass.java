package org.example;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class DndSubclass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dnd_class_id", nullable = false)
    @ToString.Exclude
    private DndClass dndClass;

    public DndSubclass(String name, String description, DndClass dndClass) {
        this.name = name;
        this.description = description;
        this.dndClass = dndClass;
    }
}