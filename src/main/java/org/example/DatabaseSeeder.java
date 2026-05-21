package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseSeeder {

    // Make sure SpellRepository is in this parameter list!
    @Bean
    CommandLineRunner initDatabase(DndClassRepository classRepository, SpellRepository spellRepository) {
        return args -> {
            if (classRepository.count() == 0) {
                DndClass barbarian = new DndClass(
                        "Barbarian",
                        "For some, their rage springs from a communion with fierce animal spirits. Others draw from a roiling reservoir of anger at a world full of pain. For every barbarian, rage is a power that fuels not just a battle frenzy but also uncanny reflexes, resilience, and feats of strength.\n\nYou must have a Strength score of 13 or higher in order to multiclass in or out of this class.",
                        "d12", "Strength", "Strength & Constitution"
                );

                // Barbarian Progression: level, PB, rages, rageDamage, cantrips, spells, slots 1-9
                barbarian.getProgression().add(new DndClassProgression(barbarian, 1, 2, 2, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 2, 2, 2, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 3, 2, 3, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 4, 2, 3, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 5, 3, 3, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 6, 3, 4, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 7, 3, 4, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 8, 3, 4, "+2", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 9, 4, 4, "+3", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 10, 4, 4, "+3", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 11, 4, 4, "+3", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 12, 4, 5, "+3", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 13, 5, 5, "+3", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 14, 5, 5, "+3", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 15, 5, 5, "+3", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 16, 5, 5, "+4", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 17, 6, 6, "+4", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 18, 6, 6, "+4", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 19, 6, 6, "+4", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
                barbarian.getProgression().add(new DndClassProgression(barbarian, 20, 6, 99, "+4", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

                barbarian.getActions().add(new DndAction("Rage", "Class Feature", 1, "In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action...", barbarian, 7, 9, 3, 5, 0, 2));
                barbarian.getActions().add(new DndAction("Unarmored Defense", "Class Feature", 1, "While you are not wearing any armor, your armor class equals 10 + your Dexterity modifier + your Constitution modifier. You can use a shield and still gain this benefit.", barbarian, 0, 8, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Reckless Attack", "Class Feature", 2, "Starting at 2nd level, you can throw aside all concern for defense to attack with fierce desperation...", barbarian, 8, -5, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Danger Sense", "Class Feature", 2, "At 2nd level, you gain an uncanny sense of when things nearby aren't as they should be...", barbarian, 0, 6, 4, 0, 0, 0));

                DndAction primalPath = new DndAction("Primal Path", "Class Feature", 3, "At 3rd level, you choose a path that shapes the nature of your rage. Your choice grants you features at 3rd level and again at 6th, 10th, and 14th levels.", barbarian, 0, 0, 0, 0, 0, 0);
                barbarian.getActions().add(primalPath);
                barbarian.getActions().add(new DndAction("Primal Knowledge (Optional)", "Class Feature", 3, "When you reach 3rd level and again at 10th level, you gain proficiency in one skill of your choice from the list of skills available to barbarians at 1st level.", barbarian, 0, 0, 4, 5, 0, 0));
                barbarian.getActions().add(new DndAction("Ability Score Improvement", "Class Feature", 4, "When you reach 4th level, and again at 8th, 12th, 16th, and 19th level, you can increase one ability score of your choice by 2, or you can increase two ability scores of your choice by 1.", barbarian, 0, 0, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Extra Attack", "Class Feature", 5, "Beginning at 5th level, you can attack twice, instead of once, whenever you take the Attack action on your turn.", barbarian, 8, 0, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Fast Movement", "Class Feature", 5, "Starting at 5th level, your speed increases by 10 feet while you aren't wearing heavy armor.", barbarian, 0, 2, 4, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Feral Instinct", "Class Feature", 7, "By 7th level, your instincts are so honed that you have advantage on initiative rolls...", barbarian, 4, 4, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Instinctive Pounce (Optional)", "Class Feature", 7, "At 7th level, as part of the bonus action you take to enter your rage, you can move up to half your speed.", barbarian, 0, 0, 6, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Brutal Critical (1 die)", "Class Feature", 9, "Beginning at 9th level, you can roll one additional weapon damage die when determining the extra damage for a critical hit with a melee attack.", barbarian, 9, 0, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Relentless Rage", "Class Feature", 11, "Starting at 11th level, your rage can keep you fighting despite grievous wounds. If you drop to 0 hit points while you're raging and don't die outright, you can make a DC 10 Constitution saving throw. If you succeed, you drop to 1 hit point instead.", barbarian, 0, 10, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Brutal Critical (2 dice)", "Class Feature", 13, "Your Brutal Critical feature increases to two additional dice.", barbarian, 9, 0, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Persistent Rage", "Class Feature", 15, "Beginning at 15th level, your rage is so fierce that it ends early only if you fall unconscious or if you choose to end it.", barbarian, 4, 4, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Brutal Critical (3 dice)", "Class Feature", 17, "Your Brutal Critical feature increases to three additional dice.", barbarian, 9, 0, 0, 0, 0, 0));
                barbarian.getActions().add(new DndAction("Indomitable Might", "Class Feature", 18, "Beginning at 18th level, if your total for a Strength check is less than your Strength score, you can use that score in place of the total.", barbarian, 0, 0, 8, 4, 0, 0));
                barbarian.getActions().add(new DndAction("Primal Champion", "Class Feature", 20, "At 20th level, you embody the power of the wilds. Your Strength and Constitution scores increase by 4. Your maximum for those scores is now 24.", barbarian, 8, 8, 4, 0, 0, 0));
                classRepository.save(barbarian);



                //Sorcerer
                DndClass sorcerer = new DndClass(
                        "Sorcerer",
                        "An event in your past, or in the life of a parent or ancestor, left an indelible mark on you, infusing you with arcane magic. This font of magic, whatever its origin, fuels your spells.",
                        "d6", "Charisma", "Constitution & Charisma"
                );

                // Sorcerer Progression: level, PB, SP, null(rage dmg), cantrips, spells, slots 1-9
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 1, 2, 0, null, 4, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 2, 2, 2, null, 4, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 3, 2, 3, null, 4, 4, 4, 2, 0, 0, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 4, 2, 4, null, 5, 5, 4, 3, 0, 0, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 5, 3, 5, null, 5, 6, 4, 3, 2, 0, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 6, 3, 6, null, 5, 7, 4, 3, 3, 0, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 7, 3, 7, null, 5, 8, 4, 3, 3, 1, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 8, 3, 8, null, 5, 9, 4, 3, 3, 2, 0, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 9, 4, 9, null, 5, 10, 4, 3, 3, 3, 1, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 10, 4, 10, null, 6, 11, 4, 3, 3, 3, 2, 0, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 11, 4, 11, null, 6, 12, 4, 3, 3, 3, 2, 1, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 12, 4, 12, null, 6, 12, 4, 3, 3, 3, 2, 1, 0, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 13, 5, 13, null, 6, 13, 4, 3, 3, 3, 2, 1, 1, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 14, 5, 14, null, 6, 13, 4, 3, 3, 3, 2, 1, 1, 0, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 15, 5, 15, null, 6, 14, 4, 3, 3, 3, 2, 1, 1, 1, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 16, 5, 16, null, 6, 14, 4, 3, 3, 3, 2, 1, 1, 1, 0));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 17, 6, 17, null, 6, 15, 4, 3, 3, 3, 2, 1, 1, 1, 1));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 18, 6, 18, null, 6, 15, 4, 3, 3, 3, 3, 1, 1, 1, 1));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 19, 6, 19, null, 6, 15, 4, 3, 3, 3, 3, 2, 1, 1, 1));
                sorcerer.getProgression().add(new DndClassProgression(sorcerer, 20, 6, 20, null, 6, 15, 4, 3, 3, 3, 3, 2, 2, 1, 1));

                sorcerer.getActions().add(new DndAction("Spellcasting", "Class Feature", 1, "An event in your past, or in the life of a parent or ancestor, left an indelible mark on you, infusing you with arcane magic...", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Sorcerous Origin", "Class Feature", 1, "Choose a sorcerous origin, which describes the source of your innate magical power. Your choice grants you features when you choose it at 1st level and again at 6th, 14th, and 18th level.", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Font of Magic", "Class Feature", 2, "At 2nd level, you tap into a deep wellspring of magic within yourself. This wellspring is represented by sorcery points, which allow you to create a variety of magical effects.", sorcerer, 0, 0, 0, 0, 0, 0));

                DndAction metamagic = new DndAction("Metamagic", "Class Feature", 3, "At 3rd level, you gain the ability to twist your spells to suit your needs. You gain two of the following Metamagic options of your choice. You gain another one at 10th and 17th level.", sorcerer, 0, 0, 0, 0, 0, 0);
                sorcerer.getActions().add(metamagic);

                // Nesting all the Metamagic options as children
                DndAction carefulSpell = new DndAction("Careful Spell", "Class Feature", 3, "When you cast a spell that forces other creatures to make a saving throw, you can protect some of those creatures from the spell's full force. To do so, you spend 1 sorcery point...", sorcerer, 0, 4, 0, 0, 0, 8);
                carefulSpell.setParentAction(metamagic);
                sorcerer.getActions().add(carefulSpell);

                DndAction distantSpell = new DndAction("Distant Spell", "Class Feature", 3, "When you cast a spell that has a range of 5 feet or greater, you can spend 1 sorcery point to double the range of the spell...", sorcerer, 0, 0, 4, 0, 0, 0);
                distantSpell.setParentAction(metamagic);
                sorcerer.getActions().add(distantSpell);

                DndAction empoweredSpell = new DndAction("Empowered Spell", "Class Feature", 3, "When you roll damage for a spell, you can spend 1 sorcery point to reroll a number of the damage dice up to your Charisma modifier (minimum of one). You must use the new rolls.", sorcerer, 8, 0, 0, 0, 0, 0);
                empoweredSpell.setParentAction(metamagic);
                sorcerer.getActions().add(empoweredSpell);

                DndAction extendedSpell = new DndAction("Extended Spell", "Class Feature", 3, "When you cast a spell that has a duration of 1 minute or longer, you can spend 1 sorcery point to double its duration, to a maximum duration of 24 hours.", sorcerer, 0, 0, 6, 0, 0, 4);
                extendedSpell.setParentAction(metamagic);
                sorcerer.getActions().add(extendedSpell);

                DndAction heightenedSpell = new DndAction("Heightened Spell", "Class Feature", 3, "When you cast a spell that forces a creature to make a saving throw to resist its effects, you can spend 3 sorcery points to give one target of the spell disadvantage on its first saving throw made against the spell.", sorcerer, 9, 0, 0, 0, 0, 0);
                heightenedSpell.setParentAction(metamagic);
                sorcerer.getActions().add(heightenedSpell);

                DndAction quickenedSpell = new DndAction("Quickened Spell", "Class Feature", 3, "When you cast a spell that has a casting time of 1 action, you can spend 2 sorcery points to change the casting time to 1 bonus action for this casting.", sorcerer, 9, 0, 3, 0, 0, 0);
                quickenedSpell.setParentAction(metamagic);
                sorcerer.getActions().add(quickenedSpell);

                DndAction seekingSpell = new DndAction("Seeking Spell", "Class Feature", 3, "If you make an attack roll for a spell and miss, you can spend 2 sorcery points to reroll the d20, and you must use the new roll.", sorcerer, 7, 0, 0, 0, 0, 0);
                seekingSpell.setParentAction(metamagic);
                sorcerer.getActions().add(seekingSpell);

                DndAction subtleSpell = new DndAction("Subtle Spell", "Class Feature", 3, "When you cast a spell, you can spend 1 sorcery point to cast it without any somatic or verbal components.", sorcerer, 0, 0, 3, 9, 0, 0);
                subtleSpell.setParentAction(metamagic);
                sorcerer.getActions().add(subtleSpell);

                DndAction transmutedSpell = new DndAction("Transmuted Spell", "Class Feature", 3, "When you cast a spell that deals a type of damage from the following list, you can spend 1 sorcery point to change that damage type to one of the other listed types: acid, cold, fire, lightning, poison, thunder.", sorcerer, 6, 0, 0, 0, 0, 0);
                transmutedSpell.setParentAction(metamagic);
                sorcerer.getActions().add(transmutedSpell);

                DndAction twinnedSpell = new DndAction("Twinned Spell", "Class Feature", 3, "When you cast a spell that targets only one creature and doesn't have a range of self, you can spend a number of sorcery points equal to the spell's level to target a second creature in range with the same spell...", sorcerer, 8, 0, 5, 0, 0, 4);
                twinnedSpell.setParentAction(metamagic);
                sorcerer.getActions().add(twinnedSpell);

                sorcerer.getActions().add(new DndAction("Ability Score Improvement", "Class Feature", 4, "When you reach 4th level, and again at 8th, 12th, 16th, and 19th level, you can increase one ability score of your choice by 2, or you can increase two ability scores of your choice by 1. As normal, you can't increase an ability score above 20 using this feature.", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Sorcerous Versatility (Optional)", "Class Feature", 4, "When you reach a level in this class that grants the Ability Score Improvement feature, you can replace one of the options you chose for the Metamagic feature, or replace one cantrip you learned from this class' spellcasting feature.", sorcerer, 0, 0, 4, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Magical Guidance (Optional)", "Class Feature", 5, "When you reach 5th level, you can tap into your inner wellspring of magic to try and conjure success from failure. When you make an ability check that fails, you can spend 1 sorcery point to reroll the d20...", sorcerer, 0, 0, 8, 7, 0, 0));
                sorcerer.getActions().add(new DndAction("Sorcerous Origin Feature", "Class Feature", 6, "At 6th level, you gain a feature granted by your Sorcerous Origin.", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Metamagic", "Class Feature", 10, "At 10th level, you gain another Metamagic option of your choice.", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Sorcerous Origin Feature", "Class Feature", 14, "At 14th level, you gain a feature granted by your Sorcerous Origin.", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Metamagic", "Class Feature", 17, "At 17th level, you gain another Metamagic option of your choice.", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Sorcerous Origin Feature", "Class Feature", 18, "At 18th level, you gain a feature granted by your Sorcerous Origin.", sorcerer, 0, 0, 0, 0, 0, 0));
                sorcerer.getActions().add(new DndAction("Sorcerous Restoration", "Class Feature", 20, "At 20th level, you regain 4 expended sorcery points whenever you finish a short rest.", sorcerer, 0, 0, 0, 0, 0, 0));

                classRepository.save(sorcerer);
                seedSorcererSpells(spellRepository, sorcerer);
            }
        };
    }

    // ==========================================
    // EXTRACTED METHOD FOR CLEANER CODE
    // ==========================================
    private void seedSorcererSpells(SpellRepository spellRepository, DndClass sorcerer) {
        Spell s0 = new Spell();
        s0.setName("Fire Bolt"); s0.setSpellLevel(0); s0.setSchool("Evocation"); s0.setCastingTime("1 action"); s0.setSpellRange("120 feet"); s0.setDuration("Instantaneous");
        s0.setCompV(true); s0.setCompS(true); s0.setCompM(false);
        s0.setDescription("You hurl a mote of fire at a creature or object within range. Make a ranged spell attack against the target. On a hit, the target takes 1d10 fire damage.");
        s0.setScoreDamage(3); s0.setScoreMitigation(1); s0.setScoreUtility(2); s0.setScoreRoleplay(1); s0.setScoreHealing(1); s0.setScoreUnity(1);
        s0.setClasses(List.of(sorcerer));
        s0.setDamage("1d10 fire");
        s0.setTags(new java.util.ArrayList<>(List.of("damage", "ranged", "single-target", "fire", "cantrip", "no-concentration", "attack-roll")));
        spellRepository.save(s0);

        Spell s1 = new Spell();
        s1.setName("Shield"); s1.setSpellLevel(1); s1.setSchool("Abjuration"); s1.setCastingTime("1 reaction"); s1.setSpellRange("Self"); s1.setDuration("1 round");
        s1.setCompV(true); s1.setCompS(true); s1.setCompM(false);
        s1.setDescription("An invisible barrier of magical force appears and protects you. Until the start of your next turn, you have a +5 bonus to AC.");
        s1.setScoreDamage(1); s1.setScoreMitigation(6); s1.setScoreUtility(1); s1.setScoreRoleplay(1); s1.setScoreHealing(1); s1.setScoreUnity(1);
        s1.setClasses(List.of(sorcerer));
        s1.setTags(new java.util.ArrayList<>(List.of("mitigation", "self", "reaction", "no-concentration", "short-duration", "defensive", "force")));
        spellRepository.save(s1);

        Spell s2 = new Spell();
        s2.setName("Suggestion"); s2.setSpellLevel(2); s2.setSchool("Enchantment"); s2.setCastingTime("1 action"); s2.setSpellRange("30 feet"); s2.setDuration("Concentration, up to 8 hours");
        s2.setCompV(true); s2.setCompS(false); s2.setCompM(true); s2.setMaterials("A snake's tongue and either a bit of honeycomb or a drop of sweet oil");
        s2.setDescription("You suggest a course of activity and magically influence a creature you can see within range that can hear and understand you.");
        s2.setScoreDamage(1); s2.setScoreMitigation(2); s2.setScoreUtility(5); s2.setScoreRoleplay(6); s2.setScoreHealing(1); s2.setScoreUnity(2);
        s2.setClasses(List.of(sorcerer));
        s2.setTags(new java.util.ArrayList<>(List.of("control", "single-target", "concentration", "social", "long-duration", "mind-affecting", "save-wisdom")));
        spellRepository.save(s2);

        Spell s3 = new Spell();
        s3.setName("Fireball"); s3.setSpellLevel(3); s3.setSchool("Evocation"); s3.setCastingTime("1 action"); s3.setSpellRange("150 feet"); s3.setDuration("Instantaneous");
        s3.setCompV(true); s3.setCompS(true); s3.setCompM(true); s3.setMaterials("A tiny ball of bat guano and sulfur");
        s3.setDescription("A bright streak flashes from your pointing finger to a point you choose within range then blossoms with a low roar into an explosion of flame.");
        s3.setScoreDamage(6); s3.setScoreMitigation(1); s3.setScoreUtility(2); s3.setScoreRoleplay(1); s3.setScoreHealing(1); s3.setScoreUnity(1);
        s3.setClasses(List.of(sorcerer));
        s3.setDamage("8d6 fire");
        s3.setTags(new java.util.ArrayList<>(List.of("damage", "area-damage", "fire", "ranged", "no-concentration", "save-dexterity", "iconic")));
        spellRepository.save(s3);

        Spell s4 = new Spell();
        s4.setName("Polymorph"); s4.setSpellLevel(4); s4.setSchool("Transmutation"); s4.setCastingTime("1 action"); s4.setSpellRange("60 feet"); s4.setDuration("Concentration, up to 1 hour");
        s4.setCompV(true); s4.setCompS(true); s4.setCompM(true); s4.setMaterials("A caterpillar cocoon");
        s4.setDescription("This spell transforms a creature that you can see within range into a new form.");
        s4.setScoreDamage(4); s4.setScoreMitigation(4); s4.setScoreUtility(6); s4.setScoreRoleplay(5); s4.setScoreHealing(3); s4.setScoreUnity(5);
        s4.setClasses(List.of(sorcerer));
        s4.setTags(new java.util.ArrayList<>(List.of("utility", "transformation", "concentration", "long-duration", "single-target", "save-wisdom")));
        spellRepository.save(s4);

        Spell s5 = new Spell();
        s5.setName("Hold Monster"); s5.setSpellLevel(5); s5.setSchool("Enchantment"); s5.setCastingTime("1 action"); s5.setSpellRange("90 feet"); s5.setDuration("Concentration, up to 1 minute");
        s5.setCompV(true); s5.setCompS(true); s5.setCompM(true); s5.setMaterials("A small, straight piece of iron");
        s5.setDescription("Choose a creature that you can see within range. The target must succeed on a Wisdom saving throw or be paralyzed for the duration.");
        s5.setScoreDamage(1); s5.setScoreMitigation(4); s5.setScoreUtility(6); s5.setScoreRoleplay(2); s5.setScoreHealing(1); s5.setScoreUnity(5);
        s5.setClasses(List.of(sorcerer));
        s5.setTags(new java.util.ArrayList<>(List.of("control", "single-target", "concentration", "paralysis", "long-duration", "save-wisdom", "mind-affecting")));
        spellRepository.save(s5);

        Spell s6 = new Spell();
        s6.setName("Chain Lightning"); s6.setSpellLevel(6); s6.setSchool("Evocation"); s6.setCastingTime("1 action"); s6.setSpellRange("150 feet"); s6.setDuration("Instantaneous");
        s6.setCompV(true); s6.setCompS(true); s6.setCompM(true); s6.setMaterials("A bit of fur; a piece of amber, glass, or a crystal rod; and three silver pins");
        s6.setDescription("You create a bolt of lightning that arcs toward a target of your choice that you can see within range. Three bolts then leap from that target to as many as three other targets.");
        s6.setScoreDamage(6); s6.setScoreMitigation(1); s6.setScoreUtility(2); s6.setScoreRoleplay(1); s6.setScoreHealing(1); s6.setScoreUnity(2);
        s6.setClasses(List.of(sorcerer));
        s6.setDamage("10d8 lightning");
        s6.setTags(new java.util.ArrayList<>(List.of("damage", "multi-target", "lightning", "ranged", "no-concentration", "save-dexterity")));
        spellRepository.save(s6);

        Spell s7 = new Spell();
        s7.setName("Reverse Gravity"); s7.setSpellLevel(7); s7.setSchool("Transmutation"); s7.setCastingTime("1 action"); s7.setSpellRange("100 feet"); s7.setDuration("Concentration, up to 1 minute");
        s7.setCompV(true); s7.setCompS(true); s7.setCompM(true); s7.setMaterials("A lodestone and iron filings");
        s7.setDescription("This spell reverses gravity in a 50-foot-radius, 100-foot high cylinder centered on a point within range. All creatures and objects that aren't somehow anchored to the ground in the area fall upward.");
        s7.setScoreDamage(3); s7.setScoreMitigation(4); s7.setScoreUtility(6); s7.setScoreRoleplay(2); s7.setScoreHealing(1); s7.setScoreUnity(4);
        s7.setClasses(List.of(sorcerer));
        spellRepository.save(s7);

        Spell s8 = new Spell();
        s8.setName("Dominate Monster"); s8.setSpellLevel(8); s8.setSchool("Enchantment"); s8.setCastingTime("1 action"); s8.setSpellRange("60 feet"); s8.setDuration("Concentration, up to 1 hour");
        s8.setCompV(true); s8.setCompS(true); s8.setCompM(false);
        s8.setDescription("You attempt to beguile a creature that you can see within range. It must succeed on a Wisdom saving throw or be charmed by you for the duration.");
        s8.setScoreDamage(4); s8.setScoreMitigation(3); s8.setScoreUtility(5); s8.setScoreRoleplay(6); s8.setScoreHealing(1); s8.setScoreUnity(3);
        s8.setClasses(List.of(sorcerer));
        spellRepository.save(s8);

        Spell s9 = new Spell();
        s9.setName("Wish"); s9.setSpellLevel(9); s9.setSchool("Conjuration"); s9.setCastingTime("1 action"); s9.setSpellRange("Self"); s9.setDuration("Instantaneous");
        s9.setCompV(true); s9.setCompS(false); s9.setCompM(false);
        s9.setDescription("Wish is the mightiest spell a mortal creature can cast. By simply speaking aloud, you can alter the very foundations of reality.");
        s9.setScoreDamage(6); s9.setScoreMitigation(6); s9.setScoreUtility(6); s9.setScoreRoleplay(6); s9.setScoreHealing(6); s9.setScoreUnity(6);
        s9.setClasses(List.of(sorcerer));
        spellRepository.save(s9);
    }
}