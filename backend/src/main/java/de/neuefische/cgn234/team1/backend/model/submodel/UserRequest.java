package de.neuefische.cgn234.team1.backend.model.submodel;

import java.util.List;

public record UserRequest(
        String userName,
        String password,
        List<UserWorkout> userWorkoutList
) {
}
