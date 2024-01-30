import {ChangeEvent} from "react";

type WorkoutPhotoProps = {
    savePhoto: (file: File) => void
}

export default function WorkoutPhotoUpload(props: WorkoutPhotoProps) {

    function savePhoto(event: ChangeEvent<HTMLInputElement>) {
        if (event.target.files && event.target.files.length > 0) {
            const file = event.target.files[0];
            props.savePhoto(file);
        }
    }

    return (
        <>
            <input type="file" id="selfie" accept="image/png, image/jpeg" capture="user" onChange={savePhoto}/>
        </>
    )
}