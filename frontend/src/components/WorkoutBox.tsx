import {Workout} from "../model/Workout.tsx";
import {Link} from "react-router-dom";


type WorkoutProps={
    workout: Workout
    deleteWorkout: (workout: Workout) => void;

}


export default function WorkoutBox(props:WorkoutProps){
    const workout: Workout = props.workout;


    function deleteWorkout() {
        props.deleteWorkout(workout)
    }


    return(
        <div className="WorkoutBox" key={workout.id}>
            <h3>{workout.workoutName}</h3>
            <p>{workout.workoutDescription.slice(0, 200)}</p>
            <Link to={"workouts/" + workout.id}>
                <button>INFO</button>
            </Link>
            <button type={"button"} onClick={deleteWorkout}>DELETE</button>
        </div>
    )
}