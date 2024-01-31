import axios from "axios";
import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";

export default function WorkoutEdit() {
    const {id} = useParams();
    const navigate = useNavigate();
    const [description, setDescription] = useState("");
    const [workoutName, setWorkoutName] = useState("");

    useEffect(() => {
        axios.get(`/api/workouts/${id}`)
            .then(response => {
                setDescription(response.data.workoutDescription);
                setWorkoutName(response.data.workoutName);
            });
    }, [id]);

    const saveValues = (e: React.FormEvent<HTMLFormElement>): void => {
        e.preventDefault();
        axios.put(`/api/workouts/${id}`, {
            id,
            workoutName,
            workoutDescription: description
        }).then(() => {
            navigate(`/workouts/${id}`);
        }).catch(error => {
            console.error("Error updating workout", error);
        });
    };


    return (
        <form onSubmit={saveValues} className={"WorkoutEdit-form"}>
            <h5 className={"WorkoutEdit-form-h5"}>Name:</h5>
            <input
                value={workoutName}
                onChange={(e) => setWorkoutName(e.target.value)}
                className={"WorkoutEdit-form-input"}
            />

            <h5 className={"WorkoutEdit-form-h5"}>Description:</h5>
            <input
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                className={"WorkoutEdit-form-input"}
            />

            <button className={"WorkoutEdit-save"} type="submit">Save</button>
        </form>
    );
}
