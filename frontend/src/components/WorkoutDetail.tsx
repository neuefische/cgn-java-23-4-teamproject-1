import {useParams} from "react-router-dom";
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

    return (
        <div>
            <div>Name: <em>{workoutName}</em></div>
            <div>Description: <em>{workoutDescription}</em></div>
        </div>
    )
}