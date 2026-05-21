package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Admin tab - manages spell tags and user roles.
 *
 * Auth follows the existing app convention: the caller's identity is in
 * the `?username=...` query param, and every endpoint short-circuits to
 * /dashboard if that user isn't flagged admin. This is not real security
 * (anyone can guess a username) but it matches the rest of the codebase;
 * tighten when you add sessions.
 */
@Controller
public class AdminController {

    private final UserService userService;
    private final SpellRepository spellRepository;

    public AdminController(UserService userService, SpellRepository spellRepository) {
        this.userService = userService;
        this.spellRepository = spellRepository;
    }

    private boolean guard(String username) {
        return userService.isAdmin(username);
    }

    @GetMapping("/admin")
    public String adminPage(@RequestParam("username") String username, Model model) {
        if (!guard(username)) {
            return "redirect:/dashboard?username=" + username;
        }
        model.addAttribute("username", username);
        model.addAttribute("spells", spellRepository.findAll());
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    // ---- Spell tag CRUD ----------------------------------------------------

    @PostMapping("/admin/spells/{id}/tags/add")
    public String addTag(@PathVariable("id") Integer spellId,
                         @RequestParam("username") String username,
                         @RequestParam("tag") String tag) {
        if (!guard(username)) {
            return "redirect:/dashboard?username=" + username;
        }
        String normalised = tag == null ? "" : tag.trim().toLowerCase();
        if (!normalised.isEmpty()) {
            spellRepository.findById(spellId).ifPresent(spell -> {
                if (spell.getTags() == null) {
                    spell.setTags(new java.util.ArrayList<>());
                }
                if (!spell.getTags().contains(normalised)) {
                    spell.getTags().add(normalised);
                    spellRepository.save(spell);
                }
            });
        }
        return "redirect:/admin?username=" + username + "#spell-" + spellId;
    }

    @PostMapping("/admin/spells/{id}/tags/remove")
    public String removeTag(@PathVariable("id") Integer spellId,
                            @RequestParam("username") String username,
                            @RequestParam("tag") String tag) {
        if (!guard(username)) {
            return "redirect:/dashboard?username=" + username;
        }
        spellRepository.findById(spellId).ifPresent(spell -> {
            if (spell.getTags() != null && spell.getTags().remove(tag)) {
                spellRepository.save(spell);
            }
        });
        return "redirect:/admin?username=" + username + "#spell-" + spellId;
    }

    // ---- User role management ---------------------------------------------

    @PostMapping("/admin/users/{id}/promote")
    public String promote(@PathVariable("id") Long userId,
                          @RequestParam("username") String username) {
        if (!guard(username)) {
            return "redirect:/dashboard?username=" + username;
        }
        userService.promote(userId);
        return "redirect:/admin?username=" + username + "#users";
    }

    @PostMapping("/admin/users/{id}/demote")
    public String demote(@PathVariable("id") Long userId,
                         @RequestParam("username") String username) {
        if (!guard(username)) {
            return "redirect:/dashboard?username=" + username;
        }
        // Don't allow demoting yourself - keeps you from locking yourself out.
        boolean isSelf = userService.findByUsername(username)
                .map(self -> userId.equals(self.getId()))
                .orElse(false);
        if (!isSelf) {
            userService.demote(userId);
        }
        return "redirect:/admin?username=" + username + "#users";
    }
}
