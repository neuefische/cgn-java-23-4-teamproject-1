package de.neuefische.cgn234.team1.backend.repo;

import de.neuefische.cgn234.team1.backend.model.User;
import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    List<UserWorkout> getWorkoutListByUserName(String userName);

    void deleteByUserName(String userName);

    boolean existsUserByUserName(String userName);

}
