import {useNavigate, useParams} from "react-router-dom";
import {Workout} from "../model/Workout.tsx";
import {useEffect, useState} from "react";
import axios from "axios";


export default function WorkoutDetail() {
    const params = useParams()
    const id: string = params.id as string

    const [workoutName, setWorkoutName] = useState<string>("")
    const [workoutDescription, setWorkoutDescription] = useState<string>("")

    function fetchData() {
        axios.get<Workout>("/api/workouts/" + id).then(response => {
            setWorkoutName(response.data.workoutName)
            setWorkoutDescription(response.data.workoutDescription)
        })
    }

    useEffect(() => {
        fetchData()
    }, [])

    const navigate = useNavigate();

    function goToEditPage() {
        navigate(`/workouts/${id}/edit`);
    }

    return (
        <div className="WorkOutDetails">
            <div><label>Name:</label>
                <p><em>{workoutName}</em></p>
            </div>
            <div><label>Description:</label>
                <p><em>{workoutDescription}</em></p>
            </div>
            <button onClick={goToEditPage} className={"editButton"}>Edit</button>
        </div>
    )
}