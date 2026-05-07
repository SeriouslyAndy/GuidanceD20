package org.example;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CharacterSheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Info
    private String name;
    private String race;
    private String characterClass;
    private int level;
    private String background;
    private String alignment;

    // Combat Stats
    private int armorClass;
    private int maxHitPoints;
    private int currentHp;
    private int tempHp;
    private int speed;
    private int inspiration;

    // Ability Scores
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    // Inventory & Notes
    private int gold;

    @Column(length = 2000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}