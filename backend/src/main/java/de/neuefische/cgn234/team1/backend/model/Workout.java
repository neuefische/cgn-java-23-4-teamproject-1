package de.neuefische.cgn234.team1.backend.model;

import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record Workout(
        @Id
        String id,
        String workoutName,
        String workoutDescription,
        List<String> workoutPhotos
) {
    public Workout(String workoutName, String workoutDescription) {
        this(UUID.randomUUID().toString(), workoutName, workoutDescription, Collections.emptyList());
    }

    public Workout withPhotos(List<String> allPhotos) {
        return new Workout(id(), workoutName(), workoutDescription(), allPhotos);
    }
}
