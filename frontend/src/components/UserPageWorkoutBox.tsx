import {UserWorkout} from "../model/UserWorkout.tsx";
import {User} from "../model/User.tsx";
import Modal from "react-bootstrap/Modal";
import WorkoutPhotoUpload from "./WorkoutPhotoUpload.tsx";
import {Button} from "react-bootstrap";
import React, {useState} from "react";
import axios from "axios";

type UserPageWorkoutBoxProps = {
    user: User;
    workout: UserWorkout;
    deleteWorkout: (workout: UserWorkout) => void;
}
export default function UserPageWorkoutBox({user, workout, deleteWorkout}: UserPageWorkoutBoxProps) {
    const [userPage, setUser] = useState<User>(user);
    const [editModal, setEditModal] = useState<boolean>(false);
    const [userWorkout, setUserWorkout] = useState<UserWorkout>({
        workoutPhotos: workout.workoutPhotos,
        workoutName: workout.workoutName,
        workoutDescription: workout.workoutDescription,
        workoutRepeat: workout.workoutRepeat,
        workoutSet: workout.workoutSet,
        workoutBreak: workout.workoutBreak,
        workoutTime: workout.workoutTime,
        workoutWeight: workout.workoutWeight
    });

    function onChangeUserWorkoutList(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setUserWorkout({...userWorkout, [event.target.name]: event.target.value})
    }


    const savePhoto = async (file: File) => {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("workoutName", workout.workoutName);

        try {
            const response = await axios.post('/api/upload/image/' + userPage.userName, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            setUserWorkout({...userWorkout, workoutPhotos: [...(userWorkout.workoutPhotos || []), response.data]})
        } catch (error) {
            console.error('Error uploading image', error);
        }
    }


    function addWorkout(event: React.FormEvent<HTMLButtonElement>) {


        setUser({...userPage, userWorkoutList: [...(userPage.userWorkoutList || []), userWorkout]})
        postUserWorkout(userPage.userName);
        setEditModal(false);
        console.log(event.target)

    }

    function deleteUserWorkout() {
        deleteWorkout(workout);
    }

    function postUserWorkout(userName: string) {
        axios.put(`/api/user/addWorkout?userName=${userName}`, {
            userName: userName,
            userWorkoutList: userPage.userWorkoutList
        }).then((response) => {
            setUser(response.data)
        })

    }

    return (<>
        <Modal show={editModal} onHide={() => {
            setEditModal(false);
            setUserWorkout(workout)
        }
        }>
            <Modal.Header>
                <Modal.Title>Add Your Personal Workout</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <form className="WorkoutEdit-form">
                    <h4 className={"RegisterForm-h3"}>Workout</h4>
                    <h5 className="WorkoutEdit-form-h5">Photos</h5>
                    <WorkoutPhotoUpload savePhoto={savePhoto}/>
                    {userWorkout.workoutPhotos?.map((photo, index) =>
                        <img src={photo} key={index} alt="A workout photo"/>
                    )}
                    <h5 className="WorkoutEdit-form-h5">Name</h5>
                    <input
                        name={"workoutName"}
                        type={"text"}
                        value={userWorkout.workoutName}
                        onChange={onChangeUserWorkoutList}
                        className="WorkoutEdit-form-input"
                    />
                    <h5 className="WorkoutEdit-form-h5">Description</h5>
                    <textarea rows={5}
                              name={"workoutDescription"}
                              value={userWorkout.workoutDescription}
                              onChange={onChangeUserWorkoutList}
                              className="WorkoutEdit-form-textarea"
                    />
                    <h5 className="WorkoutEdit-form-h5">Repeats</h5>
                    <input
                        name={"workoutRepeat"}
                        type={"number"}
                        value={userWorkout.workoutRepeat}
                        onChange={onChangeUserWorkoutList}
                        className="WorkoutEdit-form-input"
                    />
                    <h5 className="WorkoutEdit-form-h5">Sets</h5>
                    <input
                        name={"workoutSet"}
                        type={"number"}
                        value={userWorkout.workoutSet}
                        onChange={onChangeUserWorkoutList}
                        className="WorkoutEdit-form-input"
                    />
                    <h5 className="WorkoutEdit-form-h5">Weight</h5>
                    <input
                        name={"workoutWeight"}
                        type={"number"}
                        value={userWorkout.workoutWeight}
                        onChange={onChangeUserWorkoutList}
                        className="WorkoutEdit-form-input"
                    />
                    <h5 className="WorkoutEdit-form-h5">Time</h5>
                    <input
                        name={"workoutTime"}
                        type={"number"}
                        value={userWorkout.workoutTime}
                        onChange={onChangeUserWorkoutList}
                        className="WorkoutEdit-form-input"
                    />
                    <h5 className="WorkoutEdit-form-h5">Break</h5>
                    <input
                        name={"workoutBreak"}
                        type={"number"}
                        value={userWorkout.workoutBreak}
                        onChange={onChangeUserWorkoutList}
                        className="WorkoutEdit-form-input"
                    />
                </form>
            </Modal.Body>
            <Modal.Footer>
                <Button type={"reset"} onClick={() => {
                    setUserWorkout({
                        workoutPhotos: workout.workoutPhotos,
                        workoutName: workout.workoutName,
                        workoutDescription: workout.workoutDescription,
                        workoutRepeat: workout.workoutRepeat,
                        workoutSet: workout.workoutSet,
                        workoutBreak: workout.workoutBreak,
                        workoutTime: workout.workoutTime,
                        workoutWeight: workout.workoutWeight
                    })
                }}>
                    RESET
                </Button>
                <Button onClick={() => setEditModal(false)}>
                    CLOSE
                </Button>
                <Button type={"submit"} onClick={addWorkout}>
                    SAVE
                </Button>
            </Modal.Footer>
        </Modal>
        <div className="UserPageWorkoutBox" key={workout.workoutName}>
            <h3>{workout.workoutName}</h3>
            <p>{workout.workoutDescription}</p>
            <p>{workout.workoutRepeat}</p>
            <p>{workout.workoutSet}</p>
            <p>{workout.workoutBreak}</p>
            <p>{workout.workoutTime}</p>
            <p>{workout.workoutWeight}</p>
            <div className="UserPageWorkoutBoxButtons">
                <button type={"button"} onClick={() => {
                    setEditModal(true)
                }}>EDIT
                </button>
                <button type={"button"} onClick={deleteUserWorkout}>DELETE</button>
            </div>
        </div>
    </>)

}