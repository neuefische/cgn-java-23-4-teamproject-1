import {Workout} from "../model/Workout.tsx";


type WorkoutProps = {
    workout: Workout
    addWorkout: (workout: Workout, event: React.MouseEvent<HTMLButtonElement>) => void;
    openEdit: () => void;

}


export default function WorkoutBox(props: WorkoutProps) {
    const workout: Workout = props.workout;


    function add(event: React.MouseEvent<HTMLButtonElement>) {
        props.addWorkout(workout, event);
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