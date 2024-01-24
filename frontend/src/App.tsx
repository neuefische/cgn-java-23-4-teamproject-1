import './App.css'
import {useEffect, useState} from "react";
import {Workout} from "./model/Workout.tsx";
import axios from "axios";
import {Route, Routes} from "react-router-dom";
import WorkoutGallery from "./components/WorkoutGallery.tsx";
import AddWorkout, {WorkoutRequest} from "./components/AddWorkout.tsx";
import WorkoutDetail from "./components/WorkoutDetail.tsx";

function App() {

    const [workoutList, setWorkoutList] = useState<Workout[]>([])

    function getAllWorkouts() {
        axios.get("/api/workouts").then(response =>
            setWorkoutList(response.data))
    }

    function addWorkout(workout: WorkoutRequest) {
        axios.post("/api/workouts/add", {
            workoutName: workout.workoutName,
            workoutDescription: workout.workoutDescription
        }).then(response =>
            setWorkoutList([...workoutList, response.data]))
    }

    function deleteWorkout(workout: Workout) {
        axios.delete<boolean>(`/api/workouts/${workout.id}`).then((response) => {
                if (response.data === true) {
                    const id = workout.id;
                    setWorkoutList(workoutList.filter(workout => workout.id !== id))
                }
            }
        )
    }





    useEffect(() => {
        getAllWorkouts()
    }, [])

    return (
        <>
            <Routes>
                <Route path={"/"} element={<WorkoutGallery deleteWorkout={deleteWorkout} workoutList={workoutList}/>}/>
                <Route path={"/add"} element={<AddWorkout addWorkout={addWorkout}/>}/>
                <Route path={"workouts/:id"} element={<WorkoutDetail/>}/>
            </Routes>
        </>
    )
}

export default App
