import {ChangeEvent, useState} from "react";
import './App.css';


type AddWorkoutProps = {
    addWorkout: (workout: WorkoutRequest) => void;
}
export type WorkoutRequest = {
    workoutName: string,
    workoutDescription: string
}

export default function AddWorkout(addWorkout: AddWorkoutProps) {

    const [workoutName, setWorkoutName] = useState<string>("");
    const [workoutDescription, setWorkoutDescription] = useState<string>("");


    function onNameChange(event: ChangeEvent<HTMLInputElement>) {
        setWorkoutName(event.target.value)

    }

    function onDescriptionChange(event: ChangeEvent<HTMLInputElement>) {
        setWorkoutDescription(event.target.value)
    }

    function update(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault()

        const newWorkout: WorkoutRequest = {

            workoutName: workoutName,
            workoutDescription: workoutDescription,


        }
        addWorkout(newWorkout)
    }


    return (
        <div className="AddWorkout">
            <form key={undefined} onSubmit={update}>
                <input type={"text"} onChange={onNameChange} value={workoutName}
                       placeholder={"Workout Name here:"}>WORKOUTNAME</input>
                <input type={"text"} onChange={onDescriptionChange} value={workoutDescription}
                       placeholder={"Workout Description here:"}>WORKOUT DESCRIPTION</input>
                <button type={"submit"}>SUBMIT</button>
            </form>
        </div>

    )
}
