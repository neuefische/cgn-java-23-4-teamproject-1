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
    const [user, setUser] = useState<User>()
    const [loggedIn, setLoggedIn] = useState<boolean>(false)
    const [loggedInUser, setLoggedInUser] = useState<string>("")

    function getAllWorkouts() {
        axios.get("/api/workouts").then(response =>
            setWorkoutList(response.data))
    }

    function getUser() {
        axios.get("/user/me").then(response => {
            setUser(response.data)
            setLoggedIn(true)
        }).catch(() => {
            setUser(undefined)
            setLoggedIn(false)
        })
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
        getUser()
    }, [])

    function login(provider: "google" | "github") {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + '/oauth2/authorization/' + provider, '_self')

        setLoggedIn(true);
    }

    function logout() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + '/logout', '_self')
    }

    return (
        <>
            <div className="NAVBAR">

                <Link to="/"><h1>WORKOUT BUDDY</h1></Link>

                <Link to="/add">Add Workout</Link>
                {(!loggedInUser || loggedInUser === 'anonymousUser') &&
                    <button onClick={() => login("google")}>Google Login</button>}
                {(!loggedInUser || loggedInUser === 'anonymousUser') &&
                    <button onClick={() => login("github")}>Github Login</button>}
                {(loggedInUser && loggedInUser !== 'anonymousUser') && <h2>{loggedInUser}
                    <button onClick={logout}>Logout</button>
                </h2>}
                {!loggedIn && <button type={"button"} onClick={login}>LOGIN</button>}
                {loggedIn &&
                    <Link to={`/user/:` + user?.userName}><img src={"frontend/src/assets/customer.png"}/></Link>}

            </div>

            <Routes>
                <Route path={"/"}
                       element={<WorkoutGallery deleteWorkout={deleteWorkout} workoutList={workoutList}/>}/>
                <Route path={"/add"} element={<AddWorkout addWorkout={addWorkout}/>}/>
                <Route path={"workouts/:id"} element={<WorkoutDetail/>}/>
                <Route path={"/workouts/:id/edit"} element={<WorkoutEdit/>}/>
                <Route path={"/login"} element={<LoginPage getUser={getUser}/>}/>
                <Route path={"/register"} element={<RegisterPage/>}/>
                {user && <Route path={`/user/:` + user?.userName}
                                element={<UserPage workoutList={workoutList} user={user}/>}/>}
            </Routes>
        </>
    )
}

export default App
