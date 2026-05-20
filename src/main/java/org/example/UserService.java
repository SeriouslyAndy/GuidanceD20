package org.example;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

        if (userRepository.count() == 0) {
            // Added a second 'null' at the end for the new profilePicture field
            userRepository.save(new User(null, "Lathander", "light@domain.com", "password123", null));
        }
    }

    public Optional<User> authenticate(String usernameOrEmail, String password) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .filter(u -> u.getPassword().equals(password));
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}