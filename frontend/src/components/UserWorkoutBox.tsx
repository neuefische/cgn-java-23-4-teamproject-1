import {UserWorkout} from "../model/UserWorkout.tsx";
import {Link} from "react-router-dom";

type WorkoutProps = {
    workout: UserWorkout
    deleteWorkout: (workout: UserWorkout) => void;
}

export default function UserWorkoutBox(props: WorkoutProps) {
    const workout: UserWorkout = props.workout;

    function deleteWorkout() {
        props.deleteWorkout(workout)
    }

    return (
        <div className="WorkoutBox" key={workout.workoutName}>
            <h3>{workout.workoutName}</h3>
            <p>{workout.workoutDescription.slice(0, 200)}[...]</p>
            <div className="WorkoutBoxButtons">
                <Link to={"workouts/" + workout.id}>
                    <button>INFO</button>
                </Link>
                <button type={"button"} onClick={deleteWorkout}>DELETE</button>
            </div>
        </div>
    )
}

/*
    workoutName: string,
    workoutDescription: string,
    workoutRepeat: number,
    workoutSet: number,
    workoutBreak: number,
    workoutTime: number,
    workoutWeight: number
 */