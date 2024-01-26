package de.neuefische.cgn234.team1.backend.controller;

import de.neuefische.cgn234.team1.backend.model.Workout;
import de.neuefische.cgn234.team1.backend.model.dto.RequestWorkout;
import de.neuefische.cgn234.team1.backend.model.dto.WorkoutGenerate;
import de.neuefische.cgn234.team1.backend.service.ChatGptGenerateService;
import de.neuefische.cgn234.team1.backend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;
    private final ChatGptGenerateService gptGenerateService;

    @GetMapping
    public List<Workout> getAll() {
        return workoutService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Workout createWorkout(@RequestBody RequestWorkout requestWorkout) {
        return workoutService.createWorkout(requestWorkout);
    }

    @GetMapping("{id}")
    public Workout getWorkoutById(@PathVariable String id) {
        return workoutService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteWorkout(@PathVariable String id) {
        return workoutService.deleteWorkout(id);
    }

    @PutMapping("{id}")
    public Workout editWorkout(@PathVariable String id, @RequestBody Workout workout) {
        if (!workout.id().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The id in the url does not match the request body's id");
        }

        return workoutService.editWorkout(workout);
    }

    @PostMapping("/generate")
    public Workout generateWorkout(@RequestBody WorkoutGenerate workoutGenerate) {
        String[] arr = gptGenerateService.generateExercise(workoutGenerate.title());
        return workoutService.createWorkout(new RequestWorkout(
                arr[0],
                arr[1]
        ));
    }
}
