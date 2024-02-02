package de.neuefische.cgn234.team1.backend.model.submodel;


import lombok.With;

import java.util.Collections;
import java.util.List;


public record UserWorkout(
        @With
        List<String> workoutPhotos,
        String workoutName,
        String workoutDescription,
        Long workoutRepeat,
        Long workoutSet,
        Long workoutBreak,
        Long workoutTime,
        Long workoutWeight
) {

    public UserWorkout(String workoutName, String workoutDescription, long workoutRepeat, long workoutSet, long workoutBreak, long workoutTime, long workoutWeight) {
        this(Collections.emptyList(), workoutName, workoutDescription, workoutRepeat, workoutSet, workoutBreak, workoutTime, workoutWeight);
    }

    public UserWorkout(
            List<String> workoutPhotos, String workoutName, String workoutDescription, Long workoutRepeat, Long workoutSet, Long workoutBreak, Long workoutTime, Long workoutWeight) {
        this.workoutPhotos = workoutPhotos;
        this.workoutName = workoutName;
        this.workoutDescription = workoutDescription;
        this.workoutRepeat = workoutRepeat;
        this.workoutSet = workoutSet;
        this.workoutBreak = workoutBreak;
        this.workoutTime = workoutTime;
        this.workoutWeight = workoutWeight;
    }
}
