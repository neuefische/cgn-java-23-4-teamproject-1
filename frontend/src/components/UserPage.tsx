import {User} from "../model/User.tsx";
import {Workout} from "../model/Workout.tsx";
import React, {useState} from "react";
import {UserWorkout} from "../model/UserWorkout.tsx";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import WorkOutChooser from "./WorkoutChooser.tsx";
import axios from "axios";
import UserPageWorkoutBox from "./UserPageWorkoutBox.tsx";
import WorkoutPhotoUpload from "./WorkoutPhotoUpload.tsx";

type UserPageProps = {
    user: User;
    workoutList: Workout[]
}

export default function UserPage({user, workoutList}: UserPageProps) {
    const [userWorkoutList, setUserWorkoutList] = useState<UserWorkout[]>([])
    const [workoutName, setWorkoutName] = useState<string>("");
    const [workoutPhotos, setWorkoutPhotos] = useState<string[]>([]);
    const [workoutDescription, setWorkoutDescription] = useState<string>("");
    const [modal, setModal] = useState<boolean>(false);
    const [editModal, setEditModal] = useState<boolean>(false);
    const [workoutRepeats, setWorkoutRepeats] = useState<number>(0);
    const [workoutSets, setWorkoutSets] = useState<number>(0);
    const [workoutWeight, setWorkoutWeight] = useState<number>(0);
    const [workoutTime, setWorkoutTime] = useState<number>(0);
    const [workoutBreak, setWorkoutBreak] = useState<number>(0);
    const [stateWorkoutList, setStateWorkoutList] = useState<Workout>();
    const userPage: User = {
        userName: user.userName,
        userWorkoutList: userWorkoutList
    };

    function onChangeWorkoutName(event: React.ChangeEvent<HTMLInputElement>) {
        setWorkoutName(event.target.value);
    }

    function onChangeWorkoutDescription(event: React.ChangeEvent<HTMLTextAreaElement>) {
        setWorkoutDescription(event.target.value);
    }

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
        setStateWorkoutList(workout)
    }

    function openEdit() {
        setModal(false);
        setEditModal(true);
    }

    const savePhoto = async (file: File) => {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("workoutName", workoutName);

        try {
            const response = await axios.post('/api/upload/image/' + user.userName, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            setWorkoutPhotos([response.data, ...(workoutPhotos ? workoutPhotos : [])])
        } catch (error) {
            console.error('Error uploading image', error);
        }
    }
    function deleteWorkout(workout: UserWorkout) {
        axios.delete<boolean>(`/user/deleteWorkout?userName=${user.userName}`, {
            data: {
                workoutPhotos: workout.workoutPhotos,
                workoutName: workout.workoutName,
                workoutDescription: workout.workoutDescription,
                workoutRepeat: workoutRepeats,
                workoutSet: workoutSets,
                workoutBreak: workoutBreak,
                workoutTime: workoutTime,
                workoutWeight: workoutWeight
            }
        }).then((response) => {
                if (response.data === true) {
                    setUserWorkoutList(userWorkoutList.filter(workout => workout.workoutName !== workout.workoutName))
                }
            }
        )
    }

    function addWorkout(event: React.FormEvent<HTMLButtonElement>) {
        const userWorkout: UserWorkout = {
            workoutPhotos: workoutPhotos,
            workoutName: workoutName,
            workoutDescription: workoutDescription,
            workoutRepeat: workoutRepeats,
            workoutSet: workoutSets,
            workoutBreak: workoutBreak,
            workoutTime: workoutTime,
            workoutWeight: workoutWeight
        }
        setUserWorkoutList([...userWorkoutList, userWorkout]);
        userPage.userWorkoutList = userWorkoutList;
        postUserWorkout(userPage, userPage.userName);
        setEditModal(false);
        setStateWorkoutList(undefined)
        console.log(event.target)

    }

    function postUserWorkout(user: User, userName: string) {
        axios.post(`/user/addWorkout?userName= ${userName}`, {
            userName: user.userName,
            userWorkoutList: userWorkoutList
        }).then((response) => {
            setUserWorkoutList(response.data)
        })

    }


    return (
        <div className="UserPage">
            <Button onClick={() => setModal(true)}>Add Workout</Button>
            <Modal show={modal} onHide={() => setModal(false)}>
                <WorkOutChooser workoutList={workoutList} addStateWorkoutList={addStateWorkoutList}
                                openEdit={openEdit}/>
            </Modal>
            <Modal show={editModal} onHide={() => {
            }}>
                <Modal.Header>
                    <Modal.Title>Add Your Personal Workout</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="WorkoutEdit-form">
                        <h4 className={"RegisterForm-h3"}>Workout</h4>
                        <h5 className="WorkoutEdit-form-h5">Photos</h5>
                        <WorkoutPhotoUpload savePhoto={savePhoto}/>
                        {workoutPhotos?.map((photo, index) =>
                            <img src={photo} key={index} alt="A workout photo"/>
                        )}
                        <h5 className="WorkoutEdit-form-h5">Name</h5>
                        <input
                            type={"text"}
                            value={stateWorkoutList?.workoutName || ""}
                            onChange={onChangeWorkoutName}
                            className="WorkoutEdit-form-input"
                        >{stateWorkoutList?.workoutName || ""}</input>
                        <h5 className="WorkoutEdit-form-h5">Description</h5>
                        <textarea rows={5}
                                  value={stateWorkoutList?.workoutDescription || ""}
                                  onChange={onChangeWorkoutDescription}
                                  className="WorkoutEdit-form-textarea"
                        >{stateWorkoutList?.workoutDescription || ""}</textarea>
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
                        RESET
                    </Button>
                    <Button variant="secondary" onClick={() => setEditModal(false)}>
                        CLOSE
                    </Button>
                    <Button variant="primary" type={"submit"} onSubmit={addWorkout}>
                        SAVE
                    </Button>
                </Modal.Footer>
            </Modal>
            <div className="UserWorkout">
                <h2>{userPage.userName}</h2>
                <h3>Workouts</h3>
                <div className="UserWorkoutBox">
                    {userWorkoutList.map((workout) => (
                        <UserPageWorkoutBox user={userPage} workout={workout} deleteWorkout={deleteWorkout}
                                            editWorkout={postUserWorkout}/>
                    ))}
                </div>
            </div>

        </div>)
}