package org.example;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class DndAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String type; // e.g., "Spell", "Class Feature", "Attack"
    private int level;   // 0 for Cantrips/Base Features, 1-9 for Spells

    @Column(columnDefinition = "TEXT")
    private String description;

    // --- The 6-Axis Scoring System (0 to 10) ---
    private int scoreDamage = 0;
    private int scoreMitigation = 0; // Damage denial, AC buffs, resistances
    private int scoreUtility = 0;    // Crowd control, environmental manipulation
    private int scoreRoleplay = 0;   // Out-of-combat usefulness, social interactions
    private int scoreHealing = 0;    // Restoring HP
    private int scoreUnity = 0;      // 0 = purely selfish, 10 = highly selfless/party-wide

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dnd_class_id", nullable = false)
    @ToString.Exclude // Prevents infinite recursion crashes
    private DndClass dndClass;

    public DndAction(String name, String type, int level, String description, DndClass dndClass,
                     int damage, int mitigation, int utility, int roleplay, int healing, int unity) {
        this.name = name;
        this.type = type;
        this.level = level;
        this.description = description;
        this.dndClass = dndClass;
        this.scoreDamage = damage;
        this.scoreMitigation = mitigation;
        this.scoreUtility = utility;
        this.scoreRoleplay = roleplay;
        this.scoreHealing = healing;
        this.scoreUnity = unity;
    }
}