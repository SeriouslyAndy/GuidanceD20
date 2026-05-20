package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(DndClassRepository classRepository) {
        return args -> {
            if (classRepository.count() == 0) {
                String barbarianHtml = """
                    <p><em>For some, their rage springs from a communion with fierce animal spirits. Others draw from a roiling reservoir of anger at a world full of pain. For every barbarian, rage is a power that fuels not just a battle frenzy but also uncanny reflexes, resilience, and feats of strength.</em></p>
                    <p><em>You must have a Strength score of 13 or higher in order to multiclass in or out of this class.</em></p>
                    
                    <h2>The Barbarian</h2>
                    <table class="progression-table">
                        <thead>
                            <tr><th>Level</th><th>Proficiency Bonus</th><th>Features</th><th>Rages</th><th>Rage Damage</th></tr>
                        </thead>
                        <tbody>
                            <tr><td>1st</td><td>+2</td><td>Rage, Unarmored Defense</td><td>2</td><td>+2</td></tr>
                            <tr><td>2nd</td><td>+2</td><td>Reckless Attack, Danger Sense</td><td>2</td><td>+2</td></tr>
                            <tr><td>3rd</td><td>+2</td><td>Primal Path, Primal Knowledge (Optional)</td><td>3</td><td>+2</td></tr>
                            <tr><td>4th</td><td>+2</td><td>Ability Score Improvement</td><td>3</td><td>+2</td></tr>
                            <tr><td>5th</td><td>+3</td><td>Extra Attack, Fast Movement</td><td>3</td><td>+2</td></tr>
                            <tr><td>6th</td><td>+3</td><td>Path feature</td><td>4</td><td>+2</td></tr>
                            <tr><td>7th</td><td>+3</td><td>Feral Instinct, Instinctive Pounce (Optional)</td><td>4</td><td>+2</td></tr>
                            <tr><td>8th</td><td>+3</td><td>Ability Score Improvement</td><td>4</td><td>+2</td></tr>
                            <tr><td>9th</td><td>+4</td><td>Brutal Critical (1 die)</td><td>4</td><td>+3</td></tr>
                            <tr><td>10th</td><td>+4</td><td>Path feature, Primal Knowledge (Optional)</td><td>4</td><td>+3</td></tr>
                            <tr><td>11th</td><td>+4</td><td>Relentless Rage</td><td>4</td><td>+3</td></tr>
                            <tr><td>12th</td><td>+4</td><td>Ability Score Improvement</td><td>5</td><td>+3</td></tr>
                            <tr><td>13th</td><td>+5</td><td>Brutal Critical (2 dice)</td><td>5</td><td>+3</td></tr>
                            <tr><td>14th</td><td>+5</td><td>Path feature</td><td>5</td><td>+3</td></tr>
                            <tr><td>15th</td><td>+5</td><td>Persistent Rage</td><td>5</td><td>+3</td></tr>
                            <tr><td>16th</td><td>+5</td><td>Ability Score Improvement</td><td>5</td><td>+4</td></tr>
                            <tr><td>17th</td><td>+6</td><td>Brutal Critical (3 dice)</td><td>6</td><td>+4</td></tr>
                            <tr><td>18th</td><td>+6</td><td>Indomitable Might</td><td>6</td><td>+4</td></tr>
                            <tr><td>19th</td><td>+6</td><td>Ability Score Improvement</td><td>6</td><td>+4</td></tr>
                            <tr><td>20th</td><td>+6</td><td>Primal Champion</td><td>Unlimited</td><td>+4</td></tr>
                        </tbody>
                    </table>

                    <h2>Class Features</h2>
                    <p>As a barbarian, you gain the following class features.</p>
                    
                    <h3>Hit Points</h3>
                    <ul>
                        <li><strong>Hit Dice:</strong> 1d12 per barbarian level</li>
                        <li><strong>Hit Points at 1st Level:</strong> 12 + your Constitution modifier</li>
                        <li><strong>Hit Points at Higher Levels:</strong> 1d12 (or 7) + your Constitution modifier per barbarian level after 1st</li>
                    </ul>

                    <h3>Proficiencies</h3>
                    <ul>
                        <li><strong>Armor:</strong> Light armor, medium armor, shields</li>
                        <li><strong>Weapons:</strong> Simple weapons, martial weapons</li>
                        <li><strong>Saving Throws:</strong> Strength, Constitution</li>
                        <li><strong>Skills:</strong> Choose two from Animal Handling, Athletics, Intimidation, Nature, Perception, and Survival</li>
                    </ul>

                    <h3>Rage</h3>
                    <p>In battle, you fight with primal ferocity. On your turn, you can enter a rage as a bonus action. While raging, you gain the following benefits if you aren't wearing heavy armor:</p>
                    <ul>
                        <li>You have advantage on Strength checks and Strength saving throws.</li>
                        <li>When you make a melee weapon attack using Strength, you gain a bonus to the damage roll that increases as you gain levels as a barbarian.</li>
                        <li>You have resistance to bludgeoning, piercing, and slashing damage.</li>
                        <li>If you are able to cast spells, you can't cast them or concentrate on them while raging.</li>
                    </ul>
                    """;

                DndClass barbarian = new DndClass("Barbarian", barbarianHtml, "d12", "Strength", "Strength & Constitution");
                DndAction rageAction = new DndAction(
                        "Rage", "Class Feature", 1,
                        "In battle, you fight with primal ferocity. You gain advantage on Strength checks, resistance to bludgeoning, piercing, and slashing damage, and bonus melee damage.",
                        barbarian,
                        7,  // Damage (boosts melee significantly)
                        9,  // Mitigation (resistance halves damage)
                        3,  // Utility (advantage on athletics checks)
                        5,  // Roleplay (intimidation, breaking objects)
                        0,  // Healing
                        2   // Unity (Mostly selfish, but keeps the frontliner alive)
                );
                barbarian.getActions().add(rageAction);
                classRepository.save(barbarian);
            }
        };
    }
}