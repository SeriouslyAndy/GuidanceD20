package org.example;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

        // Seed the default user on first boot. The admin user is seeded
        // unconditionally because we want it to exist even if the DB was
        // created before isAdmin existed - we only skip it if it's already
        // there to keep boots idempotent.
        if (userRepository.count() == 0) {
            // (id, username, email, password, profilePicture, isAdmin)
            userRepository.save(new User(null, "Lathander", "light@domain.com",
                    "password123", null, false));
        }
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(new User(null, "admin", "admin@domain.com",
                    "123andy123", null, true));
        }
    }

    public Optional<User> authenticate(String usernameOrEmail, String password) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .filter(u -> u.getPassword().equals(password));
    }

    public void registerUser(User user) {
        if (user.getIsAdmin() == null) user.setIsAdmin(false);
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    /** Convenience: true iff the named user exists and is flagged admin. */
    public boolean isAdmin(String username) {
        if (username == null) return false;
        return userRepository.findByUsername(username)
                .map(u -> Boolean.TRUE.equals(u.getIsAdmin()))
                .orElse(false);
    }

    /** Promote a user to admin. Returns true if changed. */
    public boolean promote(Long userId) {
        return userRepository.findById(userId).map(u -> {
            if (Boolean.TRUE.equals(u.getIsAdmin())) return false;
            u.setIsAdmin(true);
            userRepository.save(u);
            return true;
        }).orElse(false);
    }

    /** Demote an admin back to a regular user. Returns true if changed. */
    public boolean demote(Long userId) {
        return userRepository.findById(userId).map(u -> {
            if (!Boolean.TRUE.equals(u.getIsAdmin())) return false;
            u.setIsAdmin(false);
            userRepository.save(u);
            return true;
        }).orElse(false);
    }
}
