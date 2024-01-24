package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    public List<Workout> getAll() {
        return workoutService.getAll();
    }

    @GetMapping("{id}")
    public Workout getWorkoutById(@PathVariable String id) {
        return workoutService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteWorkout(@PathVariable String id) {
        return workoutService.deleteWorkout(id);
    }
}
