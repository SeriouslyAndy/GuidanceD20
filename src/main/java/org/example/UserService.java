package org.example;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> mockedDatabase = new ArrayList<>();

    public UserService() {
        // Initial mock data
        mockedDatabase.add(new User("1", "Lathander", "light@domain.com", "password123"));
    }

    public Optional<User> authenticate(String usernameOrEmail, String password) {
        return mockedDatabase.stream()
                .filter(u -> (u.getUsername().equals(usernameOrEmail) || u.getEmail().equals(usernameOrEmail))
                        && u.getPassword().equals(password))
                .findFirst();
    }

    public void registerUser(User user) {
        user.setId(String.valueOf(mockedDatabase.size() + 1));
        mockedDatabase.add(user);
    }
}