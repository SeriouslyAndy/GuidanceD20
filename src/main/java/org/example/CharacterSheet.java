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
    private String hitDice;

    // Ability Scores
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    // Wealth
    private int copper;
    private int silver;
    private int electrum;
    private int gold;
    private int platinum;

    // Proficiencies & Traits
    @Column(length = 2000)
    private String proficienciesAndLanguages;
    @Column(length = 2000)
    private String featuresAndTraits;

    // Roleplay Elements
    @Column(length = 1000)
    private String personalityTraits;
    @Column(length = 1000)
    private String ideals;
    @Column(length = 1000)
    private String bonds;
    @Column(length = 1000)
    private String flaws;

    @Column(length = 2000)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}