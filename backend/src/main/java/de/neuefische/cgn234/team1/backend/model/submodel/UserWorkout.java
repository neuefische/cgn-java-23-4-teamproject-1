package de.neuefische.cgn234.team1.backend.model.submodel;


import lombok.With;

import java.util.Collections;
import java.util.List;




public record UserWorkout(
        @With
        List<String> workoutPhotos,
        String workoutName,
        String workoutDescription,
        long workoutRepeat,
        long workoutSet,
        long workoutBreak,
        long workoutTime,
        long workoutWeight
) {

    public UserWorkout(String workoutName, String workoutDescription, long workoutRepeat, long workoutSet, long workoutBreak, long workoutTime, long workoutWeight) {
        this(Collections.emptyList(), workoutName, workoutDescription, workoutRepeat, workoutSet, workoutBreak, workoutTime, workoutWeight);
    }

}
