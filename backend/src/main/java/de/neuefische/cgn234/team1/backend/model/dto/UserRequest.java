package de.neuefische.cgn234.team1.backend.model.dto;

import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;

import java.util.List;

public record UserRequest(
        String userName,
        List<UserWorkout> userWorkoutList


) {
}
