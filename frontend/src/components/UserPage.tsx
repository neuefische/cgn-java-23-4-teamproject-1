import {User} from "../model/User.tsx";
import {Workout} from "../model/Workout.tsx";
import {useState} from "react";
import {UserWorkout} from "../model/UserWorkout.tsx";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import WorkOutChooser from "./WorkoutChooser.tsx";
import UserWorkoutBox from "./UserWorkoutBox.tsx";

type UserPageProps = {
    user: User;
    workoutList: Workout[]
}

export default function UserPage({user, workoutList}: UserPageProps) {
    const [userWorkoutList, setUserWorkoutList] = useState<UserWorkout[]>([])
    const [modal, setModal] = useState<boolean>(false);
    const [editModal, setEditModal] = useState<boolean>(false);
    const [workoutRepeats, setWorkoutRepeats] = useState<number>(0);
    const [workoutSets, setWorkoutSets] = useState<number>(0);
    const [workoutWeight, setWorkoutWeight] = useState<number>(0);
    const [workoutTime, setWorkoutTime] = useState<number>(0);
    const [workoutBreak, setWorkoutBreak] = useState<number>(0);
    const [stateWorkoutList, setStateWorkoutList] = useState<Workout[]>([]);

    function onChangeWorkoutRepeats(event: React.ChangeEvent<HTMLInputElement>) {
        setWorkoutRepeats(Number(event.target.value));
    }

    function onChangeWorkoutSets(event: React.ChangeEvent<HTMLInputElement>) {
        setWorkoutSets(Number(event.target.value));
    }

    function onChangeWorkoutWeight(event: React.ChangeEvent<HTMLInputElement>) {
        setWorkoutWeight(Number(event.target.value));
    }

    function onChangeWorkoutTime(event: React.ChangeEvent<HTMLInputElement>) {
        setWorkoutTime(Number(event.target.value));
    }

    function onChangeWorkoutBreak(event: React.ChangeEvent<HTMLInputElement>) {
        setWorkoutBreak(Number(event.target.value));
    }

    function addStateWorkoutList(workout: Workout) {
        setStateWorkoutList([...stateWorkoutList, workout])
    }

    function openEdit() {
        setModal(false);
        setEditModal(true);
    }

    function addWorkout(workout: Workout, event: React.MouseEvent<HTMLButtonElement>) {
        let userWorkout: UserWorkout = {
            workoutName: workout.workoutName,
            workoutDescription: workout.workoutDescription,
            workoutRepeat: workoutRepeats,
            workoutSet: workoutSets,
            workoutBreak: workoutBreak,
            workoutTime: workoutTime,
            workoutWeight: workoutWeight
        }

    }


        

    return (
        <>
            <Button onClick={() => setModal(true)}>Add Workout</Button>
            <Modal show={modal} onHide={() => setModal(false)}>
                <WorkOutChooser workoutList={workoutList} addStateWorkoutList={addStateWorkoutList}/>
            </Modal>
            <Modal show={editModal} onHide={() => {
            }}>
                <Modal.Header>
                    <Modal.Title>Add Your Personal Workout</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="WorkoutEdit-form">
                        <h5 className="WorkoutEdit-form-h5">Repeats</h5>
                        <input
                            value={workoutRepeats}
                            onChange={onChangeWorkoutRepeats}
                            className="WorkoutEdit-form-input"
                        />
                        <h5 className="WorkoutEdit-form-h5">Sets</h5>
                        <input
                            value={workoutSets}
                            onChange={onChangeWorkoutSets}
                            className="WorkoutEdit-form-input"
                        />
                        <h5 className="WorkoutEdit-form-h5">Weight</h5>
                        <input
                            value={workoutWeight}
                            onChange={onChangeWorkoutWeight}
                            className="WorkoutEdit-form-input"
                        />
                        <h5 className="WorkoutEdit-form-h5">Time</h5>
                        <input
                            value={workoutTime}
                            onChange={onChangeWorkoutTime}
                            className="WorkoutEdit-form-input"
                        />
                        <h5 className="WorkoutEdit-form-h5">Break</h5>
                        <input
                            value={workoutBreak}
                            onChange={onChangeWorkoutBreak}
                            className="WorkoutEdit-form-input"
                        />
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button type={"reset"} onClick={() => {
                    }}>
                        reset
                    </Button>
                    <Button variant="secondary" onClick={() => setEditModal(false)}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={openEdit}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
            <div className="WorkoutGallery">
                <h2>{user.userName}</h2>
                <h3>Workouts</h3>
                <div className="WorkoutGallery-boxes">
                    {user.userWorkoutList.map(userWorkout => <UserWorkoutBox userWorkout={userWorkout}/>)}
                </div>
        </>

            )
} 