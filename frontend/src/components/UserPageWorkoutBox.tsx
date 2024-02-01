import {UserWorkout} from "../model/UserWorkout.tsx";
import {User} from "../model/User.tsx";

type UserPageWorkoutBoxProps = {
    user: User;
    workout: UserWorkout;
    editWorkout: (user: User, userName: string) => void;
    deleteWorkout: (workout: UserWorkout) => void;
}
export default function UserPageWorkoutBox({user, workout, editWorkout, deleteWorkout}: UserPageWorkoutBoxProps) {


    function deleteWorkoutButton() {
        deleteWorkout(workout)
    }

    function editWorkoutButton() {
        editWorkout(user, user.userName)
    }

    return (
        <div className="UserPageWorkoutBox" key={workout.workoutName}>
            <h3>{workout.workoutName}</h3>
            <p>{workout.workoutDescription}</p>
            <p>{workout.workoutRepeat}</p>
            <p>{workout.workoutSet}</p>
            <p>{workout.workoutBreak}</p>
            <p>{workout.workoutTime}</p>
            <p>{workout.workoutWeight}</p>
            <div className="UserPageWorkoutBoxButtons">
                <button type={"button"} onClick={editWorkoutButton}>EDIT</button>
                <button type={"button"} onClick={deleteWorkoutButton}>DELETE</button>
            </div>
        </div>)


}