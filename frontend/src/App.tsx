import {useEffect, useState} from "react";
import {Workout} from "./model/Workout.tsx";
import axios from "axios";
import {Link, Route, Routes} from "react-router-dom";
import WorkoutGallery from "./components/WorkoutGallery.tsx";
import AddWorkout, {WorkoutRequest} from "./components/AddWorkout.tsx";
import WorkoutDetail from "./components/WorkoutDetail.tsx";
import WorkoutEdit from "./components/WorkoutEdit.tsx";
import "bootstrap/dist/css/bootstrap.min.css";
import './App.css'
import LoginPage from "./components/LoginPage.tsx";
import UserPage from "./components/UserPage.tsx";
import RegisterPage from "./components/RegisterPage.tsx";
import {User} from "./model/User.tsx";


function App() {


    const [workoutList, setWorkoutList] = useState<Workout[]>([])
    const [user, setUser] = useState<User>({})
    const [loggedIn, setLoggedIn] = useState<boolean>(false)

    function getAllWorkouts() {
        axios.get("/api/workouts").then(response =>
            setWorkoutList(response.data))
    }

    useEffect(() => {
        axios.get("/user").then(response => {

        }
    }


    function addWorkout(workout: WorkoutRequest) {
        axios.post("/api/workouts", {
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
            <div className="NAVBAR">

                <Link to="/"><h1>WORKOUT BUDDY</h1></Link>
                <Link to="/add">Add Workout</Link>
                {!loggedIn && <Link to="/login">Login</Link>}
            </div>

            <Routes>
                <Route path={"/"}
                       element={<WorkoutGallery deleteWorkout={deleteWorkout} workoutList={workoutList}/>}/>
                <Route path={"/add"} element={<AddWorkout addWorkout={addWorkout}/>}/>
                <Route path={"workouts/:id"} element={<WorkoutDetail/>}/>
                <Route path={"/workouts/:id/edit"} element={<WorkoutEdit/>}/>
                <Route path={"/login"} element={<LoginPage/>}/>
                <Route path={"/register"} element={<RegisterPage/>}/>
                <Route path={`/user/:${user.username}`} element={<UserPage workoutList={workoutList} user={{}}/>}/>
            </Routes>
        </>
    )
}

export default App
