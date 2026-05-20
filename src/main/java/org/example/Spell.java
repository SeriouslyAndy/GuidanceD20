package org.example;

import jakarta.persistence.*;
import lombok.Data;
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

    // Links the many-to-many relationship using your junction table
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "spell_classes",
            joinColumns = @JoinColumn(name = "spell_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<SpellClass> classes;
}