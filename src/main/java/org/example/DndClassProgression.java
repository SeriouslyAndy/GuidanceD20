package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "class_progression")
@Data
@NoArgsConstructor
public class DndClassProgression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dnd_class_id", nullable = false)
    @ToString.Exclude
    private DndClass dndClass;

    @Column(name = "class_level", nullable = false)
    private int classLevel;

    @Column(name = "proficiency_bonus", nullable = false)
    private int proficiencyBonus;

    @Column(name = "class_resource")
    private int classResource; // e.g., Sorcery Points, Rages

    @Column(name = "secondary_class_resource")
    private String secondaryClassResource; // e.g., "+2" for Rage Damage

    @Column(name = "cantrips_known")
    private int cantripsKnown;

    @Column(name = "spells_known")
    private int spellsKnown;

    private int slot1;
    private int slot2;
    private int slot3;
    private int slot4;
    private int slot5;
    private int slot6;
    private int slot7;
    private int slot8;
    private int slot9;

    public DndClassProgression(DndClass dndClass, int classLevel, int proficiencyBonus, int classResource, String secondaryClassResource, int cantripsKnown, int spellsKnown, int slot1, int slot2, int slot3, int slot4, int slot5, int slot6, int slot7, int slot8, int slot9) {
        this.dndClass = dndClass;
        this.classLevel = classLevel;
        this.proficiencyBonus = proficiencyBonus;
        this.classResource = classResource;
        this.secondaryClassResource = secondaryClassResource;
        this.cantripsKnown = cantripsKnown;
        this.spellsKnown = spellsKnown;
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        this.slot4 = slot4;
        this.slot5 = slot5;
        this.slot6 = slot6;
        this.slot7 = slot7;
        this.slot8 = slot8;
        this.slot9 = slot9;
    }
}