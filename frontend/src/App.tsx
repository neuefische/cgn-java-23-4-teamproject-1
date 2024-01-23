
import './App.css'
import {useEffect, useState} from "react";
import {Workout} from "./model/Workout.tsx";
import  axios from "axios";
import {Route, Routes} from "react-router-dom";
import WorkoutGallery from "./components/WorkoutGallery.tsx";

function App() {

    const [workoutList, setWorkoutList] = useState<Workout[]>([])
    function getAllWorkouts(){
        axios.get("/api/workouts").then(response =>
        setWorkoutList([...workoutList, response.data]))
    }

    useEffect(() => {
        getAllWorkouts()
    },[])

    return(
        <>

            <Routes>
                <Route path={"/"} element={<WorkoutGallery workoutList={workoutList}/>}/>
            </Routes>
        </>
    )



}

export default App
