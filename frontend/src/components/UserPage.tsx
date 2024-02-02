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
    workouts: Workout[]
}

export default function UserPage({user, workouts}: UserPageProps) {


    const [userWorkout, setUserWorkout] = useState<UserWorkout>({
        workoutPhotos: [],
        workoutName: "",
        workoutDescription: "",
        workoutRepeat: 0,
        workoutSet: 0,
        workoutBreak: 0,
        workoutTime: 0,
        workoutWeight: 0
    });


    const [modal, setModal] = useState<boolean>(false);

    const [editModal, setEditModal] = useState<boolean>(false);

    const [stateWorkoutList, setStateWorkoutList] = useState<Workout>({
        id: "",
        workoutName: "",
        workoutDescription: "",
        workoutPhotos: []
    });

    const [userPage, setUser] = useState<User>(user);

    function onChangeUserWorkoutList(event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
        setUserWorkout({...userWorkout, [event.target.name]: event.target.value})
    }


    function addStateWorkoutList(workout: Workout) {
        setUserWorkout({...userWorkout, ...workout});

    }

    function openEdit() {
        setModal(false);
        setEditModal(true);
    }

    const savePhoto = async (file: File) => {
        const formData = new FormData();
        formData.append("file", file);
        formData.append("workoutName", stateWorkoutList.workoutName);

        try {
            const response = await axios.post<string>('/api/upload/image/' + userPage.userName, formData, {
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
        setStateWorkoutList({id: "", workoutName: "", workoutDescription: ""})
        console.log(event.target)

    }

    function postUserWorkout(userName: string) {
        axios.put(`/api/user/addWorkout`, {
            userName: userName,
            userWorkoutList: [...(userPage.userWorkoutList || []), userWorkout]
        }).then((response) => {
            setUser(response.data)
        })

    }

    function deleteWorkout(workout: UserWorkout) {
        axios.delete<boolean>(`/api/user/deleteWorkout?userName=${userPage.userName}`, {
                data: {...workout}
            }
        ).then((response) => {
                console.log(response)
                if (response.status === 204) {
                    setUser({
                        ...userPage,
                        userWorkoutList: userPage.userWorkoutList?.filter(workout1 => workout1.workoutName !== workout.workoutName)
                    })
                }
            }
        )
    }


    return (
        <div className="UserPage">

            <Modal show={modal} onHide={() => setModal(false)}>
                <WorkOutChooser workoutList={workouts} addStateWorkoutList={addStateWorkoutList}
                                openEdit={openEdit}/>
            </Modal>
            <Modal show={editModal} onHide={() => {
                setEditModal(false);
                setStateWorkoutList({id: "", workoutName: "", workoutDescription: ""})
            }}>
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
                            workoutPhotos: stateWorkoutList.workoutPhotos || [],
                            workoutName: stateWorkoutList.workoutName || "",
                            workoutDescription: stateWorkoutList.workoutDescription || "",
                            workoutRepeat: 0,
                            workoutSet: 0,
                            workoutBreak: 0,
                            workoutTime: 0,
                            workoutWeight: 0
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
            <div className="UserWorkout">
                <h3>{userPage.userName}</h3>
                <h2>Workouts</h2>
                <Button onClick={() => setModal(true)}>Add Workout</Button>
                <div className="UserWorkoutBox">
                    {userPage.userWorkoutList?.map((workout) => (
                        <UserPageWorkoutBox key={workout.workoutName} user={userPage} deleteWorkout={deleteWorkout}
                                            workout={workout}/>
                    ))}
                </div>
            </div>

        </div>)
}