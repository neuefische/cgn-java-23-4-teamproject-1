import {Workout} from "../model/Workout.tsx";
import WorkoutBox from "./WorkoutBox.tsx";

type WorkoutGalleryProps = {
    workoutList: Workout[]
}

export default function WorkoutGallery(props:WorkoutGalleryProps){

   const workoutList: Workout[] = props.workoutList;

   return(<>
       {workoutList.map(workout => {
           <WorkoutBox workout={workout}/>
           })}
   </>)

}