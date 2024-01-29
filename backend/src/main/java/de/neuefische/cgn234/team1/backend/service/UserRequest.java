package de.neuefische.cgn234.team1.backend.service;

import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;

import java.util.List;

public record UserRequest(
        String userName,
        String password,
        List<UserWorkout> userWorkoutList
) {
}
