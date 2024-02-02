package de.neuefische.cgn234.team1.backend.model.user;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record User(
        @Id
        String id,
        String email,
        Boolean isGoogle,
        int loginCount
) {
    public User(String email, Boolean isGoogle) {
        this(UUID.randomUUID().toString(), email, isGoogle, 0);
    }

    public User(String email, Boolean isGoogle, int loginCount) {
        this(UUID.randomUUID().toString(), email, isGoogle, loginCount);
    }

    public User withLoginCountIncrement() {
        return new User(id(), email(), isGoogle(), loginCount() + 1);
    }
}
