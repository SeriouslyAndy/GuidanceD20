package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    private final UserService userService;
    private final CharacterSheetRepository characterSheetRepository;
    private final DndClassRepository classRepository;
    private final SpellRepository spellRepository;
    private final SpellzCompare spellzCompare;

    public WebController(UserService userService, CharacterSheetRepository characterSheetRepository, DndClassRepository classRepository, SpellRepository spellRepository, SpellzCompare spellzCompare) {
        this.userService = userService;
        this.characterSheetRepository = characterSheetRepository;
        this.classRepository = classRepository;
        this.spellRepository = spellRepository;
        this.spellzCompare = spellzCompare;
    }

    @GetMapping("/spell-helper")
    public String spellHelper(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);

        // Fetch all spells from MariaDB and pass them to the template
        model.addAttribute("spells", spellRepository.findAll());
        return "spell-helper";
    }

    /**
     * NAT_2_0 endpoint - returns the comparison score between two spells
     * as JSON, consumed by the spell-helper UI.
     *
     *   GET /api/compare-spells?a=<spellIdA>&b=<spellIdB>
     */
    @GetMapping("/api/compare-spells")
    @ResponseBody
    public SpellzCompare.Nat20Result compareSpells(
            @RequestParam("a") Integer aId,
            @RequestParam("b") Integer bId) {

        Spell a = spellRepository.findById(aId)
                .orElseThrow(() -> new IllegalArgumentException("Spell not found: " + aId));
        Spell b = spellRepository.findById(bId)
                .orElseThrow(() -> new IllegalArgumentException("Spell not found: " + bId));

        return spellzCompare.compare(a, b);
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
                Optional<CharacterSheet> existing = characterSheetRepository.findByIdAndUserId(id, userOpt.get().getId());
                model.addAttribute("sheet", existing.orElseGet(this::createDefaultSheet));
            } else {
                model.addAttribute("sheet", createDefaultSheet());
            }

            // Populate Dropdowns
            model.addAttribute("availableClasses", classRepository.findAll());
            model.addAttribute("availableRaces", List.of("Dragonborn", "Dwarf", "Elf", "Gnome", "Half-Elf", "Half-Orc", "Halfling", "Human", "Tiefling"));
            model.addAttribute("availableBackgrounds", List.of("Acolyte", "Charlatan", "Criminal", "Entertainer", "Folk Hero", "Guild Artisan", "Hermit", "Noble", "Outlander", "Sage", "Sailor", "Soldier", "Urchin"));
            model.addAttribute("availableAlignments", List.of("Lawful Good", "Neutral Good", "Chaotic Good", "Lawful Neutral", "True Neutral", "Chaotic Neutral", "Lawful Evil", "Neutral Evil", "Chaotic Evil"));
        }
        return "character-sheet";
    }

    @PostMapping("/character-sheet")
    public String saveCharacterSheet(@RequestParam("username") String username, @ModelAttribute CharacterSheet sheet) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (sheet.getId() != null) {
                Optional<CharacterSheet> existing = characterSheetRepository.findById(sheet.getId());
                if (existing.isPresent() && !existing.get().getUser().getId().equals(user.getId())) {
                    return "redirect:/my-characters?username=" + username;
                }
            }
            sheet.setUser(user);
            characterSheetRepository.save(sheet);
        }
        return "redirect:/my-characters?username=" + username;
    }

    @GetMapping("/settings")
    public String settingsPage(@RequestParam("username") String username, Model model) {
        model.addAttribute("username", username);
        return "settings";
    }

    @PostMapping("/settings/pfp")
    public String uploadProfilePicture(@RequestParam("username") String username,
                                       @RequestParam("file") MultipartFile file,
                                       jakarta.servlet.http.HttpServletRequest request) {
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent() && !file.isEmpty()) {
            try {
                String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                User user = userOpt.get();
                user.setProfilePicture(base64Image);
                userService.registerUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Smart Redirect: Read where the user came from and send them back there
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/dashboard?username=" + username);
    }

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