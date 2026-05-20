package org.example;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@ControllerAdvice
public class GlobalModelAdvice {

    private final UserRepository userRepository;

    public GlobalModelAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This intercepts every web request. If a username is in the URL,
    // it fetches the User from the database and makes it available to Thymeleaf.
    @ModelAttribute("currentUser")
    public User addCurrentUser(@RequestParam(value = "username", required = false) String username) {
        if (username != null) {
            Optional<User> userOpt = userRepository.findByUsername(username);
            return userOpt.orElse(null);
        }
        return null;
    }
}