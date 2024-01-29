package de.neuefische.cgn234.team1.backend.model;

import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import org.springframework.data.annotation.Id;

import java.util.List;

public record User(
        @Id
        String userName,
        String password,
        List<UserWorkout> userWorkoutList

) {

}
