import {Workout} from "./model/Workout.tsx";
import axios from "axios";
import {Link, Route, Routes} from "react-router-dom";
import WorkoutGallery from "./components/WorkoutGallery.tsx";
import AddWorkout, {WorkoutRequest} from "./components/AddWorkout.tsx";
import WorkoutDetail from "./components/WorkoutDetail.tsx";
import WorkoutEdit from "./components/WorkoutEdit.tsx";
import "bootstrap/dist/css/bootstrap.min.css";
import './App.css'
import UserPage from "./components/UserPage.tsx";
import {User} from "./model/User.tsx";
import Modal from "react-bootstrap/Modal";
import {useEffect, useState} from "react";
import ProtectedRoute from "./components/ProtectedRoute.tsx";


function App() {

    const [workoutList, setWorkoutList] = useState<Workout[]>([])
    const [user, setUser] = useState<User>()
    const [loggedIn, setLoggedIn] = useState<boolean>(false)
    const [loggedInUser, setLoggedInUser] = useState<string>("")
    const [modal, setModal] = useState<boolean>(false)

    function getAllWorkouts() {
        axios.get("/api/workouts").then(response =>
            setWorkoutList(response.data))
    }

    function getUser() {
        axios.get("/api/user/me").then(response => {
            console.log(response.data)
            setUser(response.data)
            setLoggedInUser(response.data.userName)
            if (response.data.userName !== 'anonymousUser' && response.data.userName !== undefined) {
                setLoggedIn(true)
            }
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


    }

    function logout() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin

        window.open(host + '/logout', '_self')
    }

    return (
        <>
            <div className="NAVBAR">
                <Link to="/add">Add Workout</Link>

                <Link to="/"><h1>WORKOUT BUDDY</h1></Link>


                {(loggedInUser && loggedInUser !== 'anonymousUser') && <h2>{loggedInUser}
                    <button onClick={logout}>Logout</button>
                </h2>}
                {!loggedIn && <button type={"button"} onClick={() => {
                    setModal(true)
                }}>LOGIN</button>}
                {loggedIn &&
                    <Link to={`/user/` + user?.userName}><img src={"./src/assets/customer.png"}
                                                               alt={"Profile Logo"}/></Link>}

            </div>
            <div className="ModalLogin">
                <Modal show={modal} onHide={() => {
                    setModal(false)
                }}>
                    <Modal.Header closeButton>
                        <Modal.Title>LOGIN</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>

                        <button onClick={() => login("google")}><img src={"./src/assets/google_logoin.png"}
                                                                     style={
                                                                         {
                                                                             width: "300px",
                                                                             height: "auto"
                                                                         }
                                                                     } alt="Google Login"/></button>
                        <button onClick={() => login("github")}><img src={"./src/assets/GithubLoginWeiß.png"}
                                                                     style={
                                                                         {
                                                                             width: "300px",
                                                                             height: "auto"
                                                                         }
                                                                     } alt={"Github Login"}/></button>
                    </Modal.Body>
                    <Modal.Footer>
                        <button onClick={() => {
                            setModal(false)
                        }}>Close
                        </button>
                    </Modal.Footer>
                </Modal>
            </div>
            <Routes>
                <Route path={"/"}
                       element={<WorkoutGallery deleteWorkout={deleteWorkout} workoutList={workoutList}/>}/>

                <Route element={<ProtectedRoute user={user} loggedInUser={loggedInUser}/>}>
                    <Route path={"/add"} element={<AddWorkout addWorkout={addWorkout}/>}/>
                    <Route path={"/workouts/:id/edit"} element={<WorkoutEdit/>}/>
                    <Route path={"/user/:workout.workoutname"} element={<WorkoutDetail/>}/>
                </Route>
                <Route path={"workouts/:id"} element={<WorkoutDetail/>}/>

                {user && <Route path={`/user/:` + user?.userName}
                                element={<UserPage workouts={workoutList} user={user}/>}/>}
            </Routes>
        </>
    )
}

export default App
