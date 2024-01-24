import './App.css'
import {useEffect, useState} from "react";
import {Workout} from "./model/Workout.tsx";
import axios from "axios";
import {Route, Routes} from "react-router-dom";
import WorkoutGallery from "./components/WorkoutGallery.tsx";
import WorkoutDetail from "./components/WorkoutDetail.tsx";

function App() {

    const [workoutList, setWorkoutList] = useState<Workout[]>([])
    function getAllWorkouts(){
        axios.get("/api/workouts").then(response =>
            setWorkoutList(response.data))
    }

    useEffect(() => {
        getAllWorkouts()
    },[])

    return(
        <>
            <Routes>
                <Route path={"/"} element={<WorkoutGallery workoutList={workoutList}/>}/>
                <Route path={"workouts/:id"} element={<WorkoutDetail/>}/>
            </Routes>
        </>
    )



}

export default App
