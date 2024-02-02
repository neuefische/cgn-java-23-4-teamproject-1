import {Workout} from "../model/Workout.tsx";


type WorkoutProps = {
    workout: Workout
    addWorkout: (workout: Workout) => void;
    openEdit: () => void;

}


export default function WorkoutBox(props: WorkoutProps) {
    const workout: Workout = props.workout;


    function add() {

        props.addWorkout(workout);
        props.openEdit();
    }


    return (<>
            <div className="WorkoutChooserBox" key={workout.id}>
                <h3>{workout.workoutName}</h3>
                <p>{workout.workoutDescription.slice(0, 200)}</p>
                <div className="WorkoutBoxButtons">

                    <button type={"button"} onClick={add}>ADD</button>
                </div>
            </div>
        </>
    )
}