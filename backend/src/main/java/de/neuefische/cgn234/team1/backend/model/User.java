package de.neuefische.cgn234.team1.backend.model;

import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import lombok.With;

import java.util.List;

@With
public record User(

        String userName,
        @With
        List<UserWorkout> userWorkoutList


) {

}
