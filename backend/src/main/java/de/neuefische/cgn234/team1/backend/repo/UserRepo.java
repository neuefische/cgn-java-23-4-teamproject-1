package de.neuefische.cgn234.team1.backend.repo;

import de.neuefische.cgn234.team1.backend.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Boolean existsByEmail(String email);

    User getUserByEmail(String email);
}
