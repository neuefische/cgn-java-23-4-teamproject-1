package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.model.dto.RequestWorkout;
import de.neuefische.cgn234.team1.backend.repo.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    public List<Workout> getAll() {
        return workoutRepository.findAll();
    }

    public Workout createWorkout(RequestWorkout requestWorkout) {
        Optional<Workout> isWorkout = workoutRepository.findByWorkoutName(requestWorkout.workoutName());
        if (isWorkout.isEmpty()) {
            Workout workoutToBeCreated = new Workout(requestWorkout.workoutName(), requestWorkout.workoutDescription());
            workoutRepository.save(workoutToBeCreated);
            return workoutToBeCreated;
        } else throw new IllegalArgumentException("Workout already exists");
    }

    public Workout getById(String id) {
        return workoutRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public boolean deleteWorkout(String id) {
        if (workoutRepository.existsById(id)) {
            workoutRepository.deleteById(id);
            return true;
        }
        return false; // Workout not found
    }

    public Workout editWorkout(Workout newWorkout) {
        return workoutRepository.save(newWorkout);
    }

    public void attachPhoto(String id, String photoUrl) {
        Optional<Workout> workout = workoutRepository.findById(id);
        if (workout.isPresent()) {
            Workout presentWorkout = workout.get();
            List<String> photos = presentWorkout.workoutPhotos();
            if (photos == null) {
                photos = new ArrayList<>();
            }

            photos.addFirst(photoUrl);

            workoutRepository.save(presentWorkout.withPhotos(photos));
        }
    }
}
