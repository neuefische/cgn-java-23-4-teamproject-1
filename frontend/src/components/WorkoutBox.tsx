import {Workout} from "../model/Workout.tsx";

type WorkoutProps={
    workout: Workout
}


export default function WorkoutBox(props:WorkoutProps){
    const workout: Workout = props.workout;

    return(
        <div className="WorkoutBox" key={workout.id}>
            <h3>{workout.workoutName}</h3>
            <p>{workout.workoutDescription}</p>

        </div>
    )
}