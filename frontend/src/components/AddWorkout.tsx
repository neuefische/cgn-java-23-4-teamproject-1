import {ChangeEvent, useState} from "react";


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
        addWorkout.addWorkout(newWorkout)
    }


    return (

        <form key={undefined} onSubmit={update} className="AddWorkout">
                <input type={"text"} onChange={onNameChange} value={workoutName}
                       placeholder={"Workout Name here:"}/>
                <input type={"text"} onChange={onDescriptionChange} value={workoutDescription}
                       placeholder={"Workout Description here:"} className="Description"/>
                <button type={"submit"}>SUBMIT</button>
            </form>


    )
}
