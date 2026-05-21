package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class WikiController {

    private final DndClassRepository classRepository;
    private final SpellRepository spellRepository;

    public WikiController(DndClassRepository classRepository, SpellRepository spellRepository) {
        this.classRepository = classRepository;
        this.spellRepository = spellRepository;
    }

    @GetMapping("/wiki")
    public String wikiIndex(@RequestParam(value = "username", required = false, defaultValue = "Adventurer") String username, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("allClasses", classRepository.findAll());
        return "wiki-index";
    }

    @GetMapping("/wiki/class/{className}")
    public String getClassDetails(@PathVariable String className, @RequestParam(value = "username", required = false, defaultValue = "Adventurer") String username, Model model) {
        model.addAttribute("username", username);
        Optional<DndClass> dndClassOpt = classRepository.findByNameIgnoreCase(className);

        if (dndClassOpt.isPresent()) {
            DndClass dndClass = dndClassOpt.get();

            // Sort progression by level
            List<DndClassProgression> sortedProgression = dndClass.getProgression().stream()
                    .sorted(Comparator.comparingInt(DndClassProgression::getClassLevel))
                    .collect(Collectors.toList());

            // Sort features by level
            List<DndAction> sortedFeatures = dndClass.getActions().stream()
                    .sorted(Comparator.comparingInt(DndAction::getLevel))
                    .collect(Collectors.toList());

            model.addAttribute("dndClass", dndClass);
            model.addAttribute("progression", sortedProgression);
            model.addAttribute("features", sortedFeatures);

            return "wiki-class";
        } else {
            return "redirect:/wiki?username=" + username;
        }
    }

    @GetMapping("/wiki/class/{className}/spells")
    public String getClassSpells(@PathVariable String className, @RequestParam(value = "username", required = false, defaultValue = "Adventurer") String username, Model model) {
        model.addAttribute("username", username);
        Optional<DndClass> dndClassOpt = classRepository.findByNameIgnoreCase(className);

        if (dndClassOpt.isPresent()) {
            DndClass dndClass = dndClassOpt.get();

            // Filter all spells to only include ones belonging to this specific class
            List<Spell> classSpells = spellRepository.findByClassesContaining(dndClass);

            model.addAttribute("dndClass", dndClass);
            model.addAttribute("spells", classSpells);

            return "class-spells";
        } else {
            return "redirect:/wiki?username=" + username;
        }
    }
}