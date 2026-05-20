package org.example;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "spells")
@Data
public class Spell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private SpellSource source;

    @Column(name = "spell_level")
    private Integer spellLevel;

    private String school;

    @Column(name = "casting_time")
    private String castingTime;

    @Column(name = "spell_range")
    private String spellRange;

    private String duration;

    @Column(name = "comp_v")
    private Boolean compV;

    @Column(name = "comp_s")
    private Boolean compS;

    @Column(name = "comp_m")
    private Boolean compM;

    @Column(columnDefinition = "TEXT")
    private String materials;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "higher_levels", columnDefinition = "TEXT")
    private String higherLevels;

    @Column(name = "score_damage")
    private int scoreDamage = 1;

    @Column(name = "score_mitigation")
    private int scoreMitigation = 1;

    @Column(name = "score_utility")
    private int scoreUtility = 1;

    @Column(name = "score_roleplay")
    private int scoreRoleplay = 1;

    @Column(name = "score_healing")
    private int scoreHealing = 1;

    @Column(name = "score_unity")
    private int scoreUnity = 1;

    // Flavour tags used by the NAT_2_0 T-score (admin-defined, unlimited).
    // Example: "concentration", "area-damage", "long-range", "fire".
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "spell_tags",
            joinColumns = @JoinColumn(name = "spell_id")
    )
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    // Connects directly to your main DndClass entity
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "spell_classes",
            joinColumns = @JoinColumn(name = "spell_id"),
            inverseJoinColumns = @JoinColumn(name = "dnd_class_id")
    )
    private List<DndClass> classes;
}