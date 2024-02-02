package de.neuefische.cgn234.team1.backend.model;

import de.neuefische.cgn234.team1.backend.model.submodel.UserWorkout;
import lombok.With;
import org.springframework.data.annotation.Id;

import java.util.List;

@With
public record User(

        @Id
        String userName,
        @With
        List<UserWorkout> userWorkoutList


) {

}
