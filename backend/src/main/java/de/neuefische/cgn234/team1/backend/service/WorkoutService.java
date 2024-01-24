package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.model.dto.RequestWorkout;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutService {

   private final WorkoutRepository workoutRepository;

   public List<Workout> getAll(){
       return workoutRepository.findAll();
   }

    public void createWorkout(RequestWorkout requestWorkout) {
        Optional<Workout> isWorkout = workoutRepository.findByWorkoutName(requestWorkout.workoutName());
        if (isWorkout.isEmpty()) {
            Workout workoutToBeCreated = new Workout(requestWorkout.workoutName(), requestWorkout.workoutDescription());
            workoutRepository.save(workoutToBeCreated);
        } else throw new IllegalArgumentException("Workout already exists");
    }

}
