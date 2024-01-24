package de.neuefische.cgn234.team1.backend.repo;

import de.neuefische.cgn234.team1.backend.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutRepository extends MongoRepository<Workout, String> {

    Optional<Workout> findByWorkoutName(String workoutName);
}
