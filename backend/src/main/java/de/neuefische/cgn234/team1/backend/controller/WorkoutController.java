package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.model.dto.RequestWorkout;
import de.neuefische.cgn234.team1.backend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void createWorkout(@RequestBody RequestWorkout requestWorkout) {
        workoutService.createWorkout(requestWorkout);
    }


}
