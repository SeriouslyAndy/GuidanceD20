package org.example;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class DndClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String hitDie;
    private String primaryAbility;
    private String savingThrowProficiencies;

    @OneToMany(mappedBy = "dndClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DndSubclass> subclasses = new ArrayList<>();

    // NEW: Link to the Actions/Spells table
    @OneToMany(mappedBy = "dndClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DndAction> actions = new ArrayList<>();

    @OneToMany(mappedBy = "dndClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DndClassProgression> progression = new ArrayList<>();

    public DndClass(String name, String description, String hitDie, String primaryAbility, String savingThrowProficiencies) {
        this.name = name;
        this.description = description;
        this.hitDie = hitDie;
        this.primaryAbility = primaryAbility;
        this.savingThrowProficiencies = savingThrowProficiencies;
    }
}