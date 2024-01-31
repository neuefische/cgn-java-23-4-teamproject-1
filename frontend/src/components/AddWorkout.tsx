import React, {ChangeEvent, useState} from "react";
import axios from "axios";
import Modal from "react-bootstrap/Modal";
import {LoadingSpinnerComponent} from "../assets/Spinner.tsx";


type AddWorkoutProps = {
    addWorkout: (workout: WorkoutRequest) => void;
}
export type WorkoutRequest = {
    workoutName: string,
    workoutDescription: string
}


export default function AddWorkout(addWorkout: AddWorkoutProps) {
    const [show, setShow] = useState(false);

    const [workoutName, setWorkoutName] = useState<string>("");
    const [workoutDescription, setWorkoutDescription] = useState<string>("");

    function onNameChange(event: ChangeEvent<HTMLTextAreaElement>) {
        setWorkoutName(event.target.value)
    }

    function onDescriptionChange(event: ChangeEvent<HTMLTextAreaElement>) {
        setWorkoutDescription(event.target.value)
    }

    function generate() {
        setShow(true)

        setWorkoutName("Workout Name")
        setWorkoutDescription("")

        axios.post("/api/workouts/generate", {
            title: workoutName,
        }).then(response => {
                setShow(false)
                setWorkoutName(response.data.workoutName);
                setWorkoutDescription(response.data.workoutDescription);
            }
        )
    }

    function update(event: React.MouseEvent<HTMLButtonElement>) {
        event.preventDefault()

        const newWorkout: WorkoutRequest = {

            workoutName: workoutName,
            workoutDescription: workoutDescription,


        }
        addWorkout.addWorkout(newWorkout)
        setWorkoutName("")
        setWorkoutDescription("")
    }


    return (

        <div className="divAddworkout">

            <Modal show={show} onHide={() => {
            }} className="ModalGPT">
                <Modal.Body><LoadingSpinnerComponent/></Modal.Body>
            </Modal>
            <form onSubmit={() => {
            }} className="AddWorkout">

                <label>WORKOUT</label>
                <textarea onChange={onNameChange} value={workoutName}
                          wrap={"hard"} cols={48} rows={workoutName.length / 48}
                          placeholder={"Workout Name here:"}/>
                <label>DESCRIPTION</label>
                <textarea onChange={onDescriptionChange} value={workoutDescription}
                          cols={50} rows={10}
                          placeholder={"Workout Description here:"} className="Description"/>
                <button className="generateButton" type={"button"} onClick={generate}>GENERATE</button>
                <button className="submitButton" type={"submit"} onClick={update}>SUBMIT</button>

            </form>


        </div>


    );
}




