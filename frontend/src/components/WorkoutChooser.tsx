import {Workout} from "../model/Workout.tsx";
import React from "react";
import WorkoutChooserBox from "./WorkoutChooserBox.tsx";

type WorkOutChooserProps = {
    workoutList: Workout[]
    addStateWorkoutList: (workout: Workout) => void;
    openEdit: () => void;
}

export default function WorkOutChooser({workoutList, addStateWorkoutList, openEdit}: WorkOutChooserProps) {


    function addWorkout(workout: Workout, event: React.MouseEvent<HTMLButtonElement>) {
        addStateWorkoutList(workout)
        console.log(event.target)
    }

    return (
        <>
            <h2>Workouts</h2>
            <div className="WorkOutChooser">
                {workoutList.map(workout =>
                    <WorkoutChooserBox key={workout.id} workout={workout} openEdit={openEdit} addWorkout={addWorkout}/>
                )}
            </div>

        </>)
}