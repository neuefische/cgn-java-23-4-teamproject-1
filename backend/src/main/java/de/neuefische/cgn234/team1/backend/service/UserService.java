package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.user.User;
import de.neuefische.cgn234.team1.backend.repo.UserRepo;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUser(OAuth2User user) {
        if (user == null) {
            return null;
        }

        String userEmail = user.getAttribute("email");

        if (userEmail == null || userEmail.isEmpty()) {
            return null;
        }

        boolean isReturningUser = userRepo.existsByEmail(userEmail);

        if (!isReturningUser) {
            userRepo.save(new User(userEmail.trim(), true, 1));
        } else {
            User existingUser = userRepo.getUserByEmail(userEmail);
            this.updateUser(existingUser.withLoginCountIncrement());
        }

        return userRepo.getUserByEmail(userEmail);
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }
}
