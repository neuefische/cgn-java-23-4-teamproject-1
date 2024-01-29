package de.neuefische.cgn234.team1.backend.model;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public record Workout(
        @Id
        String id,
        String workoutName,
        String workoutDescription


) {
    public Workout(String workoutName, String workoutDescription) {
        this(UUID.randomUUID().toString(), workoutName, workoutDescription);
    }
}
