package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
public class WikiController {

    private final DndClassRepository classRepository;

    public WikiController(DndClassRepository classRepository) {
        this.classRepository = classRepository;
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
        Optional<DndClass> dndClass = classRepository.findByNameIgnoreCase(className);

        if (dndClass.isPresent()) {
            model.addAttribute("dndClass", dndClass.get());
            return "wiki-class";
        } else {
            return "redirect:/wiki?username=" + username;
        }
    }
}