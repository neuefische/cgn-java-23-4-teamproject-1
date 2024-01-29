import {Workout} from "../model/Workout.tsx";
import WorkoutBox from "./WorkoutBox.tsx";

type WorkoutGalleryProps = {
    workoutList: Workout[];
    deleteWorkout: (workout: Workout) => void;
}

export default function WorkoutGallery(props:WorkoutGalleryProps){

   const workoutList: Workout[] = props.workoutList;

    return (<div className="HomeLayout">
        {workoutList.map(workout =>
            <WorkoutBox key={workout.id} workout={workout} deleteWorkout={props.deleteWorkout}/>
        )}
    </div>)
}