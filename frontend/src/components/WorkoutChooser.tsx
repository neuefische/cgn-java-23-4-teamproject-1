import {Workout} from "../model/Workout.tsx";
import WorkoutChooserBox from "./WorkoutChooserBox.tsx";


type WorkOutChooserProps = {
    workoutList: Workout[]
    addStateWorkoutList: (workout: Workout) => void;
    openEdit: () => void;
}

export default function WorkOutChooser({workoutList, addStateWorkoutList, openEdit}: WorkOutChooserProps) {


    function addWorkout(workout: Workout) {
        addStateWorkoutList(workout)
    }

    function createWorkout() {
        const createNewWorkout: Workout = {
            id: "0",
            workoutName: "New Workout",
            workoutDescription: "New Workout Description"
        }
        addStateWorkoutList(createNewWorkout)
        openEdit()
    }

    function searchWorkout(event: React.ChangeEvent<HTMLInputElement>) {
        workoutList.filter(workout => workout.workoutName.includes(event.target.value))
    }

    return (
        <>
            <h2>Workouts</h2>
            <div className="WorkOutChooser">
                <input type="text" placeholder="Search for workouts" onChange={searchWorkout}/>
                {workoutList.map(workout =>
                    <WorkoutChooserBox key={workout.id} workout={workout} openEdit={openEdit} addWorkout={addWorkout}/>
                )}
                <button onClick={createWorkout}>Create new Workout</button>
            </div>

        </>)
}