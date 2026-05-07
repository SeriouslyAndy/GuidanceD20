package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    private final UserService userService;
    private final CharacterSheetRepository characterSheetRepository;

    public WebController(UserService userService, CharacterSheetRepository characterSheetRepository) {
        this.userService = userService;
        this.characterSheetRepository = characterSheetRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String performLogin(@RequestParam("username") String loginInput, @RequestParam("password") String password, Model model) {
        Optional<User> authenticatedUser = userService.authenticate(loginInput, password);
        if (authenticatedUser.isPresent()) {
            return "redirect:/dashboard?username=" + authenticatedUser.get().getUsername();
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String performSignup(User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(value = "username", required = false, defaultValue = "Adventurer") String username, Model model) {
        model.addAttribute("username", username);
        return "dashboard";
    }

    @GetMapping("/my-characters")
    public String myCharacters(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            List<CharacterSheet> characters = characterSheetRepository.findByUserId(userOpt.get().getId());
            model.addAttribute("characters", characters);
        }
        return "my-characters";
    }

    @GetMapping("/character-sheet")
    public String characterSheet(@RequestParam("username") String username,
                                 @RequestParam(value = "id", required = false) Long id,
                                 Model model) {
        model.addAttribute("username", username);
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            if (id != null) {
                // Load existing character
                Optional<CharacterSheet> existing = characterSheetRepository.findByIdAndUserId(id, userOpt.get().getId());
                model.addAttribute("sheet", existing.orElseGet(this::createDefaultSheet));
            } else {
                // Provide a fresh template for new characters
                model.addAttribute("sheet", createDefaultSheet());
            }
        }
        return "character-sheet";
    }

    @PostMapping("/character-sheet")
    public String saveCharacterSheet(@RequestParam("username") String username, @ModelAttribute CharacterSheet sheet) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Security: If updating an existing sheet, verify the user actually owns it
            if (sheet.getId() != null) {
                Optional<CharacterSheet> existing = characterSheetRepository.findById(sheet.getId());
                if (existing.isPresent() && !existing.get().getUser().getId().equals(user.getId())) {
                    return "redirect:/my-characters?username=" + username; // Abort if they don't own it
                }
            }

            sheet.setUser(user);
            characterSheetRepository.save(sheet);
        }
        return "redirect:/my-characters?username=" + username;
    }

    // Helper method to set defaults so a new sheet isn't filled with zeros
    private CharacterSheet createDefaultSheet() {
        CharacterSheet sheet = new CharacterSheet();
        sheet.setLevel(1);
        sheet.setArmorClass(10);
        sheet.setMaxHitPoints(10);
        sheet.setCurrentHp(10);
        sheet.setSpeed(30);
        sheet.setStrength(10);
        sheet.setDexterity(10);
        sheet.setConstitution(10);
        sheet.setIntelligence(10);
        sheet.setWisdom(10);
        sheet.setCharisma(10);
        return sheet;
    }
}