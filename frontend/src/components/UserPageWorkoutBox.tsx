import {UserWorkout} from "../model/UserWorkout.tsx";

type UserPageWorkoutBoxProps = {
    userWorkout: UserWorkout
    editWorkout: () => void;
    deleteWorkout: (workout: UserWorkout) => void;
}
export default function UserPageWorkoutBox({userWorkout, editWorkout, deleteWorkout}: UserPageWorkoutBoxProps) {


}