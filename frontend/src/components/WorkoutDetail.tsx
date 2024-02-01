import {useNavigate, useParams} from "react-router-dom";
import {Workout} from "../model/Workout.tsx";
import {useEffect, useState} from "react";
import axios from "axios";
import WorkoutPhotoUpload from "./WorkoutPhotoUpload.tsx";


export default function WorkoutDetail() {
    const params = useParams()
    const id: string = params.id as string

    const [workoutName, setWorkoutName] = useState<string>("")
    const [workoutDescription, setWorkoutDescription] = useState<string>("")
    const [photos, setPhotos] = useState<string[] | undefined>([])

    function fetchData() {
        axios.get<Workout>("/api/workouts/" + id).then(response => {
            setWorkoutName(response.data.workoutName)
            setWorkoutDescription(response.data.workoutDescription)
            setPhotos(response.data.workoutPhotos)
        })
    }

    useEffect(() => {
        fetchData()
    }, [])

    const navigate = useNavigate();

    function goToEditPage() {
        navigate(`/workouts/${id}/edit`);
    }

    const savePhoto = async (file: File) => {
        const formData = new FormData();
        formData.append("file", file);

        try {
            const response = await axios.post('/api/upload/image/' + id, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            setPhotos([response.data, ...(photos ? photos : [])])
        } catch (error) {
            console.error('Error uploading image', error);
        }
    }

    return (
        <div className="WorkOutDetails">
            <div><label>Name:</label>
                <p><em>{workoutName}</em></p>
            </div>
            <div><label>Description:</label>
                <p><em>{workoutDescription}</em></p>
            </div>
            <button onClick={goToEditPage} className={"editButton"}>Edit</button>
            <WorkoutPhotoUpload savePhoto={savePhoto}/><br/>
            {photos?.map((photo, index) =>
                <img src={photo} key={index} alt="A workout photo"/>
            )}
        </div>
    )
}