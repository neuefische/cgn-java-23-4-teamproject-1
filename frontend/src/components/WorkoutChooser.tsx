import {Workout} from "../model/Workout.tsx";

type WorkOutChooserProps = {
    workoutList: Workout[]
    addStateWorkoutList: (workout: Workout) => void;
}

export default function WorkOutChooser({workoutList, addStateWorkoutList}: WorkOutChooserProps) {


    function addWorkout(workout: Workout, event: React.MouseEvent<HTMLButtonElement>) {
        addStateWorkoutList(workout)
    }

    return (
        <>
            <h2>Workouts</h2>
            <div className="WorkOutChooser">
                {workoutList.map(workout =>
                    <WorkoutChooserBox key={workout.id} workout={workout} addWorkout={addWorkout}/>
                )}
            </div>

            }