import {ChangeEvent, useState} from "react";


type AddWorkoutProps = {
    addWorkout: (workout: WorkoutRequest) => void
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

    function onDescriptionChange(event: ChangeEvent<HTMLTextAreaElement>) {
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

        <form onSubmit={update} className="AddWorkout">
            <label>WORKOUT</label>
                <input type={"text"} onChange={onNameChange} value={workoutName}
                       placeholder={"Workout Name here:"}/>
            <label>DESCRIPTION</label>
            <textarea onChange={onDescriptionChange} value={workoutDescription}
                      cols={50} rows={10}
                      placeholder={"Workout Description here:"} className="Description"/>
                <button type={"submit"}>SUBMIT</button>
            </form>


    )
}
