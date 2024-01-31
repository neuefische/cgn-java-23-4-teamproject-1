package de.neuefische.cgn234.team1.backend.model;

import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;

import java.util.List;

public record User(

        String userName,
        List<UserWorkout> userWorkoutList

) {

}
