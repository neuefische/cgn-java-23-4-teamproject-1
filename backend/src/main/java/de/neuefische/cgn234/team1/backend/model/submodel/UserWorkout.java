package de.neuefische.cgn234.team1.backend.model.submodel;

public record UserWorkout(
        String workoutName,
        String workoutDescription,
        long workoutRepeat,
        long workoutSet,
        long workoutBreak,
        long workoutTime,
        long workoutWeight
) {
}
